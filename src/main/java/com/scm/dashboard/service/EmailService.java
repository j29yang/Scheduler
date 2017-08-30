package com.scm.dashboard.service;

import java.util.Map;

/**
 * Email-related Service
 * @author l58wang
 * @since 06.23.2016
 */

public interface EmailService {

	/**
	 * Simple email sender service.
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param text email body
	 * @param isHtml content-type
	 * @return true if send successfully.
	 */
	public boolean send(String from, String to, String subject, String text, boolean isHtml);

	/**
	 * support multi-receivers.
	 * 
	 * @param from
	 * @param to
	 * @param replyTo
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param text
	 * @param isHtml
	 * @return true if send successfully.
	 */
	public boolean send(String from, String[] to, String replyTo, String[] cc, String[] bcc, String subject, String text, boolean isHtml);

	/**
	 * add freemarker template to render email body.
	 * Can add email template files under classpath:MEAT-INFO/emails
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param template
	 * @param model
	 * @param isHtml
	 * @return if send successfully.
	 */
	public boolean send(String from, String to, String subject, String template, Map<String, Object> model, boolean isHtml);

	/**
	 * more receivers and freemarkder supported.
	 * @param from
	 * @param to
	 * @param replyTo
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param template
	 * @param model
	 * @param isHtml
	 * @return if send successfully.
	 */
	public boolean send(String from, String[] to, String replyTo, String[] cc, String[] bcc, String subject, String template, Map<String, Object> model, boolean isHtml);

	/**
	 * supporting multi email body (attach file).
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param template
	 * @param model
	 * @param isHtml
	 * @return true if send successfully
	 */
	public boolean multiSend(String from, String[] to, String replyTo,
			String[] cc, String[] bcc, String subject, String template,
			Map<String, Object> model, String filename, String attachname,
			boolean isHtml);
}
