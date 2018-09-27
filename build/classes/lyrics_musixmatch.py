import requests
import json
from lyrics_api import *
import codecs

file= open("C:/Users/Lokesh Chandra/Documents/NetBeansProjects/MusicPlayer/Sample.txt","r")
str1 = file.readline()
#print str1
temp = str1.split("-")
#print temp[0]
#print temp[1]

#print("Enter Artist Name:")
artist_name = temp[1]

#print("Enter Track Name:")
track_name = temp[0]

#api_call = base_url + lyrics_matcher + format_url + track_search_parameter + track_name + artist_search_parameter + artist_name + api_key
api_call = base_url + lyrics_matcher + format_url + artist_search_parameter + artist_name + track_search_parameter + track_name + api_key
# call the api
request = requests.get(api_call)

#a = request.raise_for_status()
#print(a)
#if(request.raise_for_status()):

data = request.json()

data = data['message']['body']
#print("API call: "+ api_call)

output = data['lyrics']['lyrics_body']

#res = "".join([chr(c) for c in output])
#print output
f = codecs.open("C:/Users/Lokesh Chandra/Documents/NetBeansProjects/MusicPlayer/output.txt","w",'utf8')
f.write(output)