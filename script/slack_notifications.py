import requests
import html2text
from bs4 import BeautifulSoup
import argparse

webhook_url = "https://hooks.slack.com/services/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"
payload = {'channels': ['#ci-cd']}


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--html_report_path', type=str, required=False, default=None, help="Path to the HTML report")
    parser.add_argument('--build_number', type=str, required=False, default=None, help="CodeBuild Build Number")
    arguments = parser.parse_args()

    with open(arguments.html_report_path, "r") as f:
        text = f.read()
        soup = BeautifulSoup(text, 'html.parser')
        passed_test = soup.find_all('span', attrs={'class': 'passed'})
        failed_test = soup.find_all('span', attrs={'class': 'failed'})
        skipped_test = soup.find_all('span', attrs={'class': 'skipped'})
        error_test = soup.find_all('span', attrs={'class': 'error'})

        h = html2text.HTML2Text()
        passed = h.handle(str(passed_test[0]))
        failed = h.handle(str(failed_test[0]))
        error = h.handle(str(error_test[0]))
        skipped = h.handle(str(skipped_test[0]))

    if ("api" in arguments.html_report_path):
        title = "Book Library Management:  Tests execution details for build #" + str(arguments.build_number) + "\n API Testing Framework"
    elif ("ui" in arguments.html_report_path):
        title = "Book Library Management:  Tests execution details for build #" + str(arguments.build_number) + "\n Web Automation Testing Framework"

    if (int(failed.split(" ")[0]) != 0):
        status = "danger"
    elif (int(error.split(" ")[0]) != 0 or int(skipped.split(" ")[0]) != 0 and int(failed.split(" ")[0]) == 0):
        status = "warning"
    else:
        status = "good"

    attachments = [{
        "pretext": title,
        "color": status,
        "fields": [{
            "title": "Passed Test Cases",
            "value": passed,
            "short": True,
        }, {
            "title": "Failed Test Cases",
            "value": failed,
            "short": True
        }, {
            "title": "Erroneous Test Cases",
            "value": error,
            "short": True
        }, {
            "title": "Skipped Test Cases",
            "value": skipped,
            "short": True
        }]
    }]
    payload['attachments'] = attachments
    r = requests.post(webhook_url, json=payload)


if __name__ == "__main__":
    main()

