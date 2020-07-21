import argparse
import smtplib
import base64


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--html_report_path', type=str, required=False, default=None, help="Path to the HTML report")
    parser.add_argument('--build_number', type=str, required=False, default=None, help="CodeBuild Build Number")
    arguments = parser.parse_args()
    # Read a file and encode it into base64 format
    fo = open(arguments.html_report_path, "rb")
    filecontent = fo.read()
    encodedcontent = base64.b64encode(filecontent)  # base64

    sender = 'webmaster@tutorialpoint.com'
    reciever = 'amrood.admin@gmail.com'

    marker = "AUNIQUEMARKER"

    body ="""
    This is a test email to send an attachement.
    """
    # Define the main headers.
    part1 = """From: From Person <me@fromdomain.net>
    To: To Person <amrood.admin@gmail.com>
    Subject: Sending Attachement
    MIME-Version: 1.0
    Content-Type: multipart/mixed; boundary=%s
    --%s
    """ % (marker, marker)

    # Define the message action
    part2 = """Content-Type: text/plain
    Content-Transfer-Encoding:8bit

    %s
    --%s
    """ % (body,marker)

    # Define the attachment section
    part3 = """Content-Type: multipart/mixed; name=\"%s\"
    Content-Transfer-Encoding:base64
    Content-Disposition: attachment; filename=%s

    %s
    --%s--
    """ %(filename, filename, encodedcontent, marker)
    message = part1 + part2 + part3

    try:
       smtpObj = smtplib.SMTP('localhost')
       smtpObj.sendmail(sender, reciever, message)
       print "Successfully sent email"
    except Exception:
       print "Error: unable to send email"
        
if __name__ == "__main__":
    main()