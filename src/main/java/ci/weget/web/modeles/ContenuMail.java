package ci.weget.web.modeles;

public class ContenuMail {
private String lien="";

 private String codeValidation="";
 private String contenuHtml ="<!DOCTYPE html>\r\n" + 
 		"<html>\r\n" + 
 		"<head>\r\n" + 
 		"\r\n" + 
 		"<!-- <link href='//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css' rel='stylesheet' id='bootstrap-css'> -->\r\n" + 
 		"\r\n" + 
 		"<!-- <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'> -->\r\n" + 
 		"\r\n" + 
 		"<!-- <script src='//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js'></script> -->\r\n" + 
 		"<!-- <script src='//code.jquery.com/jquery-1.11.1.min.js'></script> -->\r\n" + 
 		"<style>\r\n" + 
 		"\r\n" + 
 		".op {\r\n" + 
 		"\r\n" + 
 			 
 		"	background-size:cover;\r\n" + 
 		"	top:0;left:0;right:0;bottom:0;\r\n" + 
 		"	height:100%;\r\n" + 
 		"	position:fixed;\r\n" + 
 		"	z-index:0;\r\n" + 
 		"	\r\n" + 
 		"}\r\n" + 
 		"\r\n" + 
 		"body {\r\n" + 
 		"    padding: 0;\r\n" + 
 		"    margin: 0;\r\n" + 
 		"	\r\n" + 
 		"	\r\n" + 
 		"}\r\n" + 
 		"\r\n" + 
 		".hed {\r\n" + 
 		"    padding: 0;\r\n" + 
 		"    margin: 0;\r\n" + 
 		"	background-image: url('https://firebasestorage.googleapis.com/v0/b/toget-2b431.appspot.com/o/bg_entete.png?alt=media&token=2da079a0-3e9a-4138-985b-f2ec173ef9a4');\r\n" + 
 		"	background-size:cover;\r\n" + 
 		"	top:0;left:0;right:0;bottom:0;\r\n" + 
 		"	\r\n" + 
 		"}\r\n" + 
 		"\r\n" + 
 		"html { -webkit-text-size-adjust:none; -ms-text-size-adjust: none;}\r\n" + 
 		"@media only screen and (max-device-width: 680px), only screen and (max-width: 680px) { \r\n" + 
 		"    *[class='table_width_100'] {\r\n" + 
 		"		width: 96% !important;\r\n" + 
 		"	}\r\n" + 
 		"	*[class='border-right_mob'] {\r\n" + 
 		"		border-right: 1px solid #dddddd;\r\n" + 
 		"	}\r\n" + 
 		"	*[class='mob_100'] {\r\n" + 
 		"		width: 100% !important;\r\n" + 
 		"	}\r\n" + 
 		"	*[class='mob_center'] {\r\n" + 
 		"		text-align: center !important;\r\n" + 
 		"	}\r\n" + 
 		"	*[class='mob_center_bl'] {\r\n" + 
 		"		float: none !important;\r\n" + 
 		"		display: block !important;\r\n" + 
 		"		margin: 0px auto;\r\n" + 
 		"	}	\r\n" + 
 		"	.iage_footer a {\r\n" + 
 		"		text-decoration: none;\r\n" + 
 		"		color: #929ca8;\r\n" + 
 		"	}\r\n" + 
 		"	img.mob_display_none {\r\n" + 
 		"		width: 0px !important;\r\n" + 
 		"		height: 0px !important;\r\n" + 
 		"		display: none !important;\r\n" + 
 		"	}\r\n" + 
 		"	img.mob_width_50 {\r\n" + 
 		"		width: 40% !important;\r\n" + 
 		"		height: auto !important;\r\n" + 
 		"	}\r\n" + 
 		"}\r\n" + 
 		".table_width_100 {\r\n" + 
 		"	width: 680px;\r\n" + 
 		"}\r\n" + 
 		"</style>\r\n" + 
 		"</head>\r\n" + 
 		"<!--  Include the above in your HEAD tag  -->\r\n" + 
 		"\r\n" + 
 		"<!--\r\n" + 
 		"Responsive Email Template by @keenthemes\r\n" + 
 		"A component of Metronic Theme - #1 Selling Bootstrap 3 Admin Theme in Themeforest: http://j.mp/metronictheme\r\n" + 
 		"Licensed under MIT\r\n" + 
 		"-->\r\n" + 
 		"<body style=''>\r\n" + 
 		"\r\n" + 
 		"\r\n" + 
 		"\r\n" + 
 		"<div class='op'></div>\r\n" + 
 		"<div id='mailsub' class='notification' style='position: relative; z-index:3;' align='center'>\r\n" + 
 		"\r\n" + 
 		"\r\n" + 
 		"<table width='100%' border='0' cellspacing='0' cellpadding='0' style='min-width: 320px;'><tr><td align='center' bgcolor=''>\r\n" + 
 		"\r\n" + 
 		"\r\n" + 
 		"<!--[if gte mso 10]>\r\n" + 
 		"<table width='680' border='0' cellspacing='0' cellpadding='0'>\r\n" + 
 		"<tr><td>\r\n" + 
 		"<![endif]-->\r\n" + 
 		"\r\n" + 
 		"<table border='0' cellspacing='0' cellpadding='0' class='table_width_100' width='100%' style='max-width: 680px; min-width: 300px;'>\r\n" + 
 		"    <tr><td>\r\n" + 
 		"	<!-- padding --><div style='height: 80px; line-height: 80px; font-size: 10px;'></div>\r\n" + 
 		"	</td></tr>\r\n" + 
 		"	<!--header -->\r\n" + 
 		"	<tr><td align='center' bgcolor='' style='position:relative;' class='hed'>\r\n" + 
 		"	\r\n" + 
 		"	\r\n" + 
 		"		<!-- padding --><div style='height: 20px; line-height: 30px; font-size: 10px;'></div>\r\n" + 
 		"		<table width='90%' border='0' cellspacing='0' cellpadding='0' style='position:relative;'>\r\n" + 
 		"			<tr><td align='left'><!-- \r\n" + 
 		"\r\n" + 
 		"				Item --><div class='mob_center_bl' style='float: left; display: inline-block; width: 115px;'>\r\n" + 
 		"					<table class='mob_center' width='115' border='0' cellspacing='0' cellpadding='0' align='left' style='border-collapse: collapse;'>\r\n" + 
 		"						<tr><td align='left' valign='middle'>\r\n" + 
 		"							<!-- padding --><div style='height: 20px; line-height: 20px; font-size: 10px;'></div>\r\n" + 
 		"							<table width='115' border='0' cellspacing='0' cellpadding='0' >\r\n" + 
 		"								<tr><td align='left' valign='top' class='mob_center'>\r\n" + 
 		"									<a href='#' target='_blank' style='color: #596167; font-family: Arial, Helvetica, sans-serif; font-size: 13px; text-decoration:none;'>\r\n" + 
 		"									<font face='Arial, Helvetica, sans-seri; font-size: 13px;' size='3' color='#596167'>\r\n" + 
 		"									<img src='http://artloglab.com/metromail/images/logo.gif' width='115' height='19' alt='WegetPro' border='0' style='display: block; color:#fff;' /></font></a>\r\n" + 
 		"								</td></tr>\r\n" + 
 		"							</table>						\r\n" + 
 		"						</td></tr>\r\n" + 
 		"					</table></div>\r\n" + 
 		"					\r\n" + 
 		"					<div class='' style='float:left; width: auto; display: inline-block; color:#fff; font-size:30px; font-weight:bold;'>\r\n" + 
 		"					WegetPro Registration Service\r\n" + 
 		"					</div>\r\n" + 
 		"					<!-- Item END--><!--[if gte mso 10]>\r\n" + 
 		"					</td>\r\n" + 
 		"					<td align='right'>\r\n" + 
 		"				<![endif]--><!-- \r\n" + 
 		"				\r\n" + 
 		"\r\n" + 
 		"				Item --><div class='mob_center_bl' style='float: right; display: inline-block; width: 88px;'>\r\n" + 
 		"					<table width='88' border='0' cellspacing='0' cellpadding='0' align='right' style='border-collapse: collapse;'>\r\n" + 
 		"						<tr><td align='right' valign='middle'>\r\n" + 
 		"							<!-- padding --><div style='height: 20px; line-height: 20px; font-size: 10px;'></div>\r\n" + 
 		"							<table width='100%' border='0' cellspacing='0' cellpadding='0' >\r\n" + 
 		"								<tr><td align='right'>\r\n" + 
 		"									<!--social -->\r\n" + 
 		"									<div class='mob_center_bl' style='width: 88px;'>\r\n" + 
 		"									<table border='0' cellspacing='0' cellpadding='0'>\r\n" + 
 		"										<tr><td width='30' align='center' style='line-height: 19px;'>\r\n" + 
 		"											<a href='#' target='_blank' style='color: #596167; font-family: Arial, Helvetica, sans-serif; font-size: 12px;'>\r\n" + 
 		"											<font face='Arial, Helvetica, sans-serif' size='2' color='#fff'>\r\n" + 
 		"											<i class='fa fa-facebook' style='color:#fff;'></i>\r\n" + 
 		"											</font></a>\r\n" + 
 		"										</td><td width='39' align='center' style='line-height: 19px;'>\r\n" + 
 		"											<a href='#' target='_blank' style='color: #596167; font-family: Arial, Helvetica, sans-serif; font-size: 12px;'>\r\n" + 
 		"											<font face='Arial, Helvetica, sans-serif' size='2' color='#fff'>\r\n" + 
 		"											<i class='fa fa-twitter' style='color:#fff; '></i>\r\n" + 
 		"											</font></a>\r\n" + 
 		"										</td></tr>\r\n" + 
 		"									</table>\r\n" + 
 		"									</div>\r\n" + 
 		"									<!--social END-->\r\n" + 
 		"								</td></tr>\r\n" + 
 		"							</table>\r\n" + 
 		"						</td></tr>\r\n" + 
 		"					</table></div><!-- Item END--></td>\r\n" + 
 		"			</tr>\r\n" + 
 		"		</table>\r\n" + 
 		"		<!-- padding --><div style='height: 50px; line-height: 50px; font-size: 10px;'></div>\r\n" + 
 		"	</td></tr>\r\n" + 
 		"	<!--header END-->\r\n" + 
 		"\r\n" + 
 		"	<!--content 1 -->\r\n" + 
 		"	<tr><td align='center' bgcolor='#fbfcfd'>\r\n" + 
 		"		<table width='90%' border='0' cellspacing='0' cellpadding='0'>\r\n" + 
 		"			<tr><td align='center'>\r\n" + 
 		"				<!-- padding --><div style='height: 60px; line-height: 60px; font-size: 10px;'></div>\r\n" + 
 		"				<div style='line-height: 44px;'>\r\n" + 
 		"					<font face='Arial, Helvetica, sans-serif' size='5' color='#57697e' style='font-size: 34px;'>\r\n" + 
 		"					<span style='font-family: Arial, Helvetica, sans-serif; font-size: 34px; color: #57697e;'>\r\n" + 
 		"						Bonjour, bienvenue à WegetPro\r\n" + 
 		"					</span></font>\r\n" + 
 		"				</div>\r\n" + 
 		"				<!-- padding --><div style='height: 40px; line-height: 40px; font-size: 10px;'></div>\r\n" + 
 		"			</td></tr>\r\n" + 
 		"			<tr><td align='center'>\r\n" + 
 		"				<div style='line-height: 24px;'>\r\n" + 
 		"					<font face='Arial, Helvetica, sans-serif' size='4' color='#57697e' style='font-size: 15px;'>\r\n" + 
 		"					<span style='font-family: Arial, Helvetica, sans-serif; font-size: 15px; color: #57697e;'>\r\n" + 
 		"						Merci pour votre inscription à la plateforme. Pour avoir un accès total à votre Dashboard veuillez copié et collé ce code ci-dessous dans le champs proposé à ce lien suivant : <a href='";
                           String    var1="' target='_blank'>Cliquez ici pour activer</a> \r\n" + 
 		"					</span></font>\r\n" + 
 		"					<div style='width:400px;  margin:15px;  display:flex; justify-content:center;'>";			
 								
 						String variable = "" +
 		"</div>\r\n" + 
 		"				</div>\r\n" + 
 		"				<!-- padding --><div style='height: 40px; line-height: 40px; font-size: 10px;'></div>\r\n" + 
 		"			</td></tr>\r\n" + 
 		"			<tr><td align='center'>\r\n" + 
 		"				<div style='line-height: 24px;'>\r\n" + 
 		"				Ce code est valable pour 24h. Passer ce delais vous devez refaire une demande depuis votre\r\n" + 
 		"					<a href='#' target='_blank' style='color: #596167; font-family: Arial, Helvetica, sans-serif; font-size: 13px;'>\r\n" + 
 		"						<font face='Arial, Helvetica, sans-seri; font-size: 13px;' size='3' color='#596167'>\r\n" + 
 		"							 dashboard.\r\n" + 
 		"							</font></a>\r\n" + 
 		"				</div>\r\n" + 
 		"				<!-- padding --><div style='height: 20px; line-height: 60px; font-size: 10px;'></div>\r\n" + 
 		"			</td></tr>\r\n" + 
 		"		</table>		\r\n" + 
 		"	</td></tr>\r\n" + 
 		"	<!--content 1 END-->\r\n" + 
 		"\r\n" + 
 		"	\r\n" + 
 		"\r\n" + 
 		"	<!--footer -->\r\n" + 
 		"	<tr><td class='iage_footer' align='center' bgcolor='#ffffff'>\r\n" + 
 		"	\r\n" + 
 		"		<!-- <hr/> -->\r\n" + 
 		"		<table width='100%' border='0' cellspacing='0' cellpadding='0'>\r\n" + 
 		"			<tr><td align='center'>\r\n" + 
 		"				<font face='Arial, Helvetica, sans-serif' size='3' color='#96a5b5' style='font-size: 13px; padding:10px;'>\r\n" + 
 		"				Localisation : Angrée 7 ième Tranche | Email: info@toget.pro | Tel: 22 422 937 / 77 950 437 | Adresse: 01 BP 13662 ABIDJAN 01<br/>\r\n" + 
 		"				<span style='font-family: Arial, Helvetica, sans-serif; font-size: 13px; color: #96a5b5;'>\r\n" + 
 		"					© 2018 Copyright: wegetpro.com \r\n" + 
 		"				</span></font>				\r\n" + 
 		"			</td></tr>			\r\n" + 
 		"		</table>\r\n" + 
 		"		\r\n" + 
 		"		<!-- padding --><div style='height: 30px; line-height: 30px; font-size: 10px;'></div>	\r\n" + 
 		"	</td></tr>\r\n" + 
 		"	<!--footer END-->\r\n" + 
 		"	<tr><td>\r\n" + 
 		"	<!-- padding --><div style='height: 80px; line-height: 80px; font-size: 10px;'></div>\r\n" + 
 		"	</td></tr>\r\n" + 
 		"</table>\r\n" + 
 		"<!--[if gte mso 10]>\r\n" + 
 		"</td></tr>\r\n" + 
 		"</table>\r\n" + 
 		"<![endif]-->\r\n" + 
 		" \r\n" + 
 		"</td></tr>\r\n" + 
 		"</table>\r\n" + 
 		"			\r\n" + 
 		"</div> \r\n" + 
 		"\r\n" + 
 		"</body>\r\n" + 
 		"</html>";
public String getCodeValidation() {
	return codeValidation;
}
public void setCodeValidation(String codeValidation) {
	this.codeValidation = codeValidation;
}

public ContenuMail() {
	super();
	// TODO Auto-generated constructor stub
}

public ContenuMail(String codeValidation, String contenuHtml) {
	super();
	this.codeValidation = codeValidation;
	this.contenuHtml = contenuHtml;
}

public ContenuMail(String lien, String codeValidation, String contenuHtml, String variable) {
	super();
	this.lien = lien;
	this.codeValidation = codeValidation;
	this.contenuHtml = contenuHtml;
	this.variable = variable;
}
public String getContenuHtml(String ex,String lien) {
	return contenuHtml+lien+var1+ex+variable;
}
public void setContenuHtml(String contenuHtml) {
	this.contenuHtml = contenuHtml;
}
public String getLien() {
	return lien;
}
public void setLien(String lien) {
	this.lien = lien;
}
public String getVariable() {
	return variable;
}
public void setVariable(String variable) {
	this.variable = variable;
}
 
 
}
