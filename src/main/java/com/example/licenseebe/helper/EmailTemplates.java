package com.example.licenseebe.helper;

public class EmailTemplates {
    public static String getVerificationEmailTemplate() {
        String emailTemplate = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body style=\"background-color:#ffffff;margin: 0px;\">\n" +
                "<div style=\"margin:0;padding:0;background-color:#F1F2F2\">\n" +
                "    <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"\n" +
                "           style=\"width:100%;Margin:0 auto;background-color:#F1F2F2\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "            <td style=\"font-size:0\"></td>\n" +
                "            <td align=\"center\" valign=\"top\" style=\"background-color:#ffffff;width: 600px;padding-left: 40px;padding-right: 40px;\">\n" +
                "\n" +
                "\n" +
                "                <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:100%\"\n" +
                "                       class=\"m_-5571692548161461954w96\">\n" +
                "                    <tbody>\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\">\n" +
                "\n" +
//                "                            <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:100%\">\n" +
//                "                                <tbody>\n" +
//                "                                <tr>\n" +
//                "                                    <td align=\"center\"\n" +
//                "                                        style=\"padding:40px 0;border-bottom:1px solid #cccccc\">\n" +
//                "\n" +
//                "                                        <img src=\"https://i.ibb.co/5vYnLr9/Screenshot-2022-08-17-at-15-04-09-removebg-preview.png\" alt=\"Screenshot-2022-08-17-at-15-04-09-removebg-preview\"\n" +
//                "                                             alt=\"CST SMOKETEST\" title=\"CST SMOKETEST\" border=\"0\"\n" +
//                "                                             style=\"display:block;outline:0;padding:0;border:0;width:160px;height:auto;\">\n" +
//                "                                    </td>\n" +
//                "                                </tr>\n" +
//                "                                </tbody>\n" +
//                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\" style=\"padding:40px 0;border-bottom:1px solid #cccccc\">\n" +
                "\n" +
                "                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:100%\">\n" +
                "                                <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td style=\"font-family: Raleway;font-size:15px;line-height:16px;color:#130C0E;\">\n" +
                "                                        {CODE},\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td style=\"padding-top:20px;font-family: Raleway;font-size:15px;line-height:16px;color:#130C0E;\">\n" +
                "                                        <p>This is your 2FA verification code</p>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>\n" +
                "                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:100%\">\n" +
                "                                <tbody>\n" +
                "                                <tr></tr>\n" +
                "                                <tr>\n" +
                "                                    <td style=\"padding-top: 10px;padding-bottom: 30px;font-size: 12px;font-family: Raleway; color: #130C0E\">\n" +
                "                                        Digital Bookcase\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    </tbody>\n" +
                "                </table>\n" +
                "\n" +
                "            </td>\n" +
                "            <td style=\"font-size:0\"></td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        return emailTemplate;
    }

    public static String getReminderEmailTemplate() {
        String emailTemplate = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body style=\"background-color:#ffffff;margin: 0px;\">\n" +
                "<div style=\"margin:0;padding:0;background-color:#F1F2F2\">\n" +
                "    <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"\n" +
                "           style=\"width:100%;Margin:0 auto;background-color:#F1F2F2\">\n" +
                "        <tbody>\n" +
                "        <tr>\n" +
                "            <td style=\"font-size:0\"></td>\n" +
                "            <td align=\"center\" valign=\"top\" style=\"background-color:#ffffff;width: 600px;padding-left: 40px;padding-right: 40px;\">\n" +
                "\n" +
                "\n" +
                "                <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:100%\"\n" +
                "                       class=\"m_-5571692548161461954w96\">\n" +
                "                    <tbody>\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\" style=\"padding:40px 0;border-bottom:1px solid #cccccc\">\n" +
                "\n" +
//                "                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:100%\">\n" +
//                "                                <tbody>\n" +
//                "                                <tr>\n" +
//                "                                    <td style=\"font-family: Raleway;font-size:15px;line-height:16px;color:#130C0E;\">\n" +
//                "                                        {CODE},\n" +
//                "                                    </td>\n" +
//                "                                </tr>\n" +
//                "                                <tr>\n" +
//                "                                </tr>\n" +
//                "                                <tr>\n" +
//                "                                    <td style=\"padding-top:20px;font-family: Raleway;font-size:15px;line-height:16px;color:#130C0E;\">\n" +
//                "                                        <p>This is your 2FA verification code</p>\n" +
//                "                                        <p style=\"margin-bottom: 0px;\">The CST minions</p>\n" +
//                "                                    </td>\n" +
//                "                                </tr>\n" +
//                "                                </tbody>\n" +
//                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>\n" +
                "                            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:100%\">\n" +
                "                                <tbody>\n" +
                "                                <tr></tr>\n" +
                "                                <tr>\n" +
                "                                    <td style=\"padding-top: 10px;padding-bottom: 30px;font-size: 12px;font-family: Raleway; color: #130C0E\">\n" +
                "                                        Digital Bookcase\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                </tbody>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    </tbody>\n" +
                "                </table>\n" +
                "\n" +
                "            </td>\n" +
                "            <td style=\"font-size:0\"></td>\n" +
                "        </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        return emailTemplate;
    }

    public static String sendVerificationEmail(String totpCode) {
        String emailTemplate = getVerificationEmailTemplate();
        emailTemplate = emailTemplate.replaceAll("\\{CODE}", totpCode);
        return emailTemplate;
    }

    public static String sendChangePasswordEmail(String generatedCode) {
        String emailTemplate = getVerificationEmailTemplate();
        emailTemplate = emailTemplate.replaceAll("\\{CODE}", generatedCode);
        return emailTemplate;
    }

    public static String sendReminderEmail() {
        return getReminderEmailTemplate();
    }
}
