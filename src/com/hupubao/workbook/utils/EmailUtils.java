package com.hupubao.workbook.utils;

import com.hupubao.workbook.bean.Setting;
import com.hupubao.workbook.enums.Style;
import com.hupubao.workbook.listener.wb.SendEmailActionListener;
import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author Moses
 * @date 2017-11-01 10:53
 */
public class EmailUtils {

    private static final SimpleDateFormat formatD = new SimpleDateFormat("yyyy-MM-dd");

    private static final String WORK_PATH = System.getProperty("user.home") + "/workbook/";

    private static final String TEMPLATE_FILE_PATH = System.getProperty("user.home") + "/workbook/template/template.xlsx";

    private static final String DEFAULT_TEMPLATE_FILE_PATH = "/template/template.xlsx";

    public static void sendEmail() throws Exception {
        // 读取配置信息
        Setting setting = SettingUtils.readSetting();
        if (setting == null || setting.getEmailSetting() == null) {
            throw new Exception("请先配置邮件设置");
        }


        String xlsFilePath = WORK_PATH + setting.getEmailSetting().getSender() + "_" + formatD.format(new Date()) + ".xls";
        File templateFile = new File(TEMPLATE_FILE_PATH);

        if (!templateFile.exists()) {
            // 外部模版不存在，复制内置模版到外部模版目录
            try {
                FileUtils.writeByteArrayToFile(templateFile, IOUtils.toByteArray(EmailUtils.class.getResourceAsStream(DEFAULT_TEMPLATE_FILE_PATH)), false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        Workbook workbook;

        try {
            workbook = new XSSFWorkbook(templateFile);
        } catch (Exception e) {
            workbook = new HSSFWorkbook(new POIFSFileSystem(templateFile));
        }


        Sheet sheet = workbook.getSheetAt(0);


        for (Row row : sheet) {
            for (Cell cell : row) {

                if (cell.getCellType() != CellType.STRING) {
                    continue;
                }

                if (cell.getRichStringCellValue().getString().contains("${department}")) {
                    cell.setCellValue(cell.getRichStringCellValue().getString().replace("${department}",
                            setting.getDepartment()));
                }

                if (cell.getRichStringCellValue().getString().contains("${title}")) {
                    cell.setCellValue(cell.getRichStringCellValue().getString().replace("${title}",
                            setting.getEmailSetting().getTitle()));
                }

                if (cell.getRichStringCellValue().getString().contains("${sender}")) {
                    cell.setCellValue(cell.getRichStringCellValue().getString().replace("${sender}",
                            setting.getEmailSetting().getSender()));
                }

                if (cell.getRichStringCellValue().getString().contains("${email}")) {
                    cell.setCellValue(cell.getRichStringCellValue().getString().replace("${email}",
                            setting.getEmailSetting().getEmail()));
                }

                if (cell.getRichStringCellValue().getString().contains("${content}")) {
                    cell.setCellValue(cell.getRichStringCellValue().getString().replace("${content}",
                            buildEmailContent(setting)));
                }
                for (int i = 1; i <= 7; i++) {
                    String dayKey = "${day" + i + "}";
                    String percentKey = "${percent" + i + "}";
                    if (cell.getRichStringCellValue().getString().contains(dayKey)) {
                        cell.setCellValue(cell.getRichStringCellValue().getString().replace(dayKey,
                                DataUtils.buildContentByDayOfWeek(i)));
                    }

                    if (cell.getRichStringCellValue().getString().contains(percentKey)) {
                        cell.setCellValue(cell.getRichStringCellValue().getString().replace(percentKey,
                                DataUtils.buildPercent(i)));
                    }
                }


            }
        }


        File file = new File(xlsFilePath);
        if (file.exists()) {
            file.delete();
        }

        String[] defaultRecievers = DataUtils.getDefaultRecievers();


        ExcelUtils.createFile(workbook, xlsFilePath);

        send(setting, defaultRecievers, xlsFilePath);
        if (file.exists()) {
            file.delete();
        }
    }



    private static void send(Setting setting,
                             String[] to,
                             String filepath) throws Exception {

        System.setProperty("mail.mime.splitlongparameters", "false");

        Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", "smtp");
        //服务器
        prop.setProperty("mail.smtp.host", setting.getEmailSetting().getHost());
        //端口
        prop.setProperty("mail.smtp.port", setting.getEmailSetting().getPort());
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getDefaultInstance(prop, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(setting.getEmailSetting().getEmail(), setting.getEmailSetting().getPassword());
            }

        });

//        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        //发件人
        mimeMessage.setFrom(new InternetAddress(setting.getEmailSetting().getEmail(), setting.getEmailSetting().getSender()));        //可以设置发件人的别名
        //收件人
        for (String emailTo : to) {
            if (StringUtils.isNotBlank(emailTo)) {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo.trim()));
            }
        }
        //主题
        mimeMessage.setSubject(setting.getEmailSetting().getTitle(), "UTF-8");
        //时间
        mimeMessage.setSentDate(new Date());

        //容器类，可以包含多个MimeBodyPart对象
        Multipart mp = new MimeMultipart();


        //MimeBodyPart可以包装文本，图片，附件
        MimeBodyPart bodyHtml = new MimeBodyPart();
        //HTML正文
        bodyHtml.setContent(buildEmailContent(setting).replace("\n", "<br/>"), "text/html; charset=UTF-8");
        mp.addBodyPart(bodyHtml);


        if (StringUtils.isNotBlank(filepath)) {
            //添加图片&附件
            MimeBodyPart bodyFile = new MimeBodyPart();
            bodyFile.attachFile(filepath);
            bodyFile.setFileName(MimeUtility.encodeText(new File(filepath).getName()));
            mp.addBodyPart(bodyFile);
        }

        //设置邮件内容
        mimeMessage.setContent(mp);
        Thread.currentThread().setContextClassLoader(SendEmailActionListener.class.getClassLoader());
        Transport.send(mimeMessage);
    }

    /**
     * <h1>解析邮件内容</h1>
     *
     * @param setting
     * @return
     */
    private static String buildEmailContent(Setting setting) {

        return setting.getEmailSetting().getContentTemplate().replace("${sender}", setting.getEmailSetting().getSender())
                .replace("${list}", Style.LIST.name().equals(setting.getStyle()) ? DataUtils.buildWBTextListString() : DataUtils.buildWBTextListStringByWeek());
    }
}
