package com.emsi.ecommerce.service;

import java.io.File;

import com.emsi.ecommerce.domaine.UserVo;

public interface IMailService {
	void sendEmailWithExcelAttachement(UserVo client) throws Exception;
	void sendValidationAccountEmail(UserVo client);

}
