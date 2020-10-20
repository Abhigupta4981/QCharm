
import argparse
import requests
import json
import sys

url_dict = {
        'load_file_v3': '{}/qcharm/v3/load_file',
        'get_page_v3': '{}/qcharm/v3/get_page'
}

def get_url_prefix(ip_address, port):
    return "http://{}:{}".format(ip_address, port)

def get_nextpage(page):
    url_prefix = get_url_prefix('localhost', 8081)

    url = url_dict['get_page_v3'].format(url_prefix)
    page_info = {
            "pageId" : page['pageId'] + 1,
            "fileName" : page['fileName']
            }

    #headers = headers = {'Content-type': 'application/json',  'Accept': 'text/plain'}
    #resp = requests.post(url,data=json.dumps(page_info), headers=headers)
    headers = headers = {'Content-type': 'application/json'}
    resp = requests.post(url,data=json.dumps(page_info), headers=headers)
    page = resp.json()
    print('PAGE = ', json.dumps(page, indent=4))
    return page

def load_file(file_name):
    file_name_short = file_name.split('/')[-1]

    multipart_file = {'file' : (file_name_short, open(file_name,'rb'))}

    url_prefix = get_url_prefix('localhost', 8081)

    url = url_dict['load_file_v3'].format(url_prefix)
    print(url)
    resp = requests.post(url,files=multipart_file)

    page = resp.json()
    print('PAGE = ', json.dumps(page, indent=4))
    return page

def down(page_id):
    #get_page_after(page_id)
    return None

def up(page_id):
    #get_page_before(page_id)
    return None

def down(page_id):
    #get_page_after(page_id)
    return None

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("--load", required=False, help="file_to_load")
    args = parser.parse_args()

    page = load_file(args.load)

    next_page = get_nextpage(page)

# 1)Add a new line at line no x
# get page: and match
#
# 2) Add few words in line
# Add few more words in line
# Change few words in line
# get page: and match
