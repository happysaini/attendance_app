import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email import encoders
import argparse

#get file from argument
parser = argparse.ArgumentParser()
parser.add_argument('--html_report_file', type=str, required=False, default=None, help="HTML report of test framework")
parser.add_argument('--password', type=str, required=False, default=None, help="password to be passed for sending email")
arguments = parser.parse_args()

#The mail addresses and password
sender_address = 'hsaini@qasource.com'
sender_pass = arguments.password
receiver_address = 'hsaini@qasource.com'

mail_content = '''Hi Team

Mobile CI/CD Pipeline has compleated. 
Please find HTML Results/Logs as attachment

Thanks
DevOps Team
'''
#Setup the MIME
message = MIMEMultipart()
message['From'] = sender_address
message['To'] = receiver_address
message['Subject'] = 'Mobile CI/CD | Test Report'
#The subject line
#The body and the attachments for the mail
message.attach(MIMEText(mail_content, 'plain'))
attach_file_name = arguments.html_report_file
attach_file = open(attach_file_name, 'rb') # Open the file as binary mode
payload = MIMEBase('application', 'octate-stream')
payload.set_payload((attach_file).read())
encoders.encode_base64(payload) #encode the attachment
#add payload header with filename
payload.add_header('Content-Decomposition', 'attachment', filename=attach_file_name)
message.attach(payload)
#Create SMTP session for sending the mail
session = smtplib.SMTP('173.247.240.172', 587)
session.starttls() #enable security
session.login(sender_address, sender_pass) #login with mail_id and password
text = message.as_string()
session.sendmail(sender_address, receiver_address, text)
session.quit()
print('Mail Sent')