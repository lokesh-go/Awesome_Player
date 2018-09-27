import moviepy.editor as mp


import os

source = "C:/Users/Lokesh Chandra/Videos"
target = "C:/Users/Lokesh Chandra/Music/Mymusic"

for subdir, dirs, files in os.walk(source):
	for file in files:
		if file.endswith((".mp4",".avi")):
			p = os.path.join(subdir,file)
			clip = mp.VideoFileClip(p).subclip(0.20)
			str1 = file.split(".")
			str2 = str1[0] + ".mp3"
			clip.audio.write_audiofile(target+"/"+str2)