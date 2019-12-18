package com.hupubao.workbook.listener.wb;

import com.hupubao.workbook.utils.ComponentUtils;
import com.hupubao.workbook.utils.DataUtils;
import com.hupubao.workbook.utils.EmailUtils;
import com.hupubao.workbook.utils.StringUtils;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

/**
 * @author Moses
 * @date 2017-10-30 15:12
 */
public class SendEmailActionListener implements ActionListener {

    private JTable tableNewWB;
    private Project currentProject;


    public SendEmailActionListener(JTable tableNewWB) {
        this.tableNewWB = tableNewWB;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentProject = ComponentUtils.getCurrentProject(tableNewWB);

        String[] defaultRecievers = DataUtils.getDefaultRecievers();
        if (defaultRecievers == null || defaultRecievers.length == 0) {
            Messages.showMessageDialog(currentProject, "还没有选择默认邮件配置呢！╭(╯^╰)╮", "提示", Messages.getInformationIcon());
            return;
        }

        String message = "即将发送邮件给：" + StringUtils.join(defaultRecievers, ";");

        int code = Messages.showOkCancelDialog(currentProject, message, "注意注意！", "发送", "不发了", Messages.getQuestionIcon());

        if (code != Messages.OK) {
            return;
        }

        CompletableFuture<Boolean>  future = CompletableFuture.supplyAsync(() -> {
            ProgressManager.getInstance().run(new Task.Backgroundable(currentProject, "发送邮件中...") {
                @Override
                public void run(@NotNull ProgressIndicator progressIndicator) {
                    //更新一次Maid-Rem数据，设为邮件版本
                    DataUtils.saveConfigs(tableNewWB, true);
                    String errMsg = "";
                    boolean successEmail = true;
                    try {

                        EmailUtils.sendEmail();
                    } catch (Exception ex) {
                        errMsg = ex.getMessage();
                        successEmail = false;
                    }


                    boolean finalSuccessEmail = successEmail;
                    String finalErrMsg = errMsg;
                    UIUtil.invokeLaterIfNeeded(() -> {
                        if (finalSuccessEmail) {
                            Messages.showMessageDialog(currentProject, "邮件已经发送了哟！QAQ", "提示", Messages.getInformationIcon());
                        } else {
                            Messages.showMessageDialog(currentProject, "糟了！邮件发送失败了！ლ(ﾟдﾟლ) \n" +
                                    "错误信息：" + finalErrMsg, "错误", Messages.getErrorIcon());
                        }
                    });
                }
            });
            return null;
        });


    }
}
