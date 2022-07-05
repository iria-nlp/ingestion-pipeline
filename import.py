#!/usr/bin/env python3

from glob import glob
import requests

for f in glob( 'docs/*.json' ):
    with open(f, encoding='utf8' ) as file:
        document = file.read()

        response = requests.post( 'http://localhost:8529/_db/dart/_api/document/canonical_docs', document.encode('utf8') )
        
        if response.status_code == 202:
            print(f'{f} was successfully uploaded')
        else:
            print(f'{file} failed')
            print(f'{response.status_code} - {response.body}')
