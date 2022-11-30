from flask import Flask, render_template, request
from werkzeug.utils import secure_filename
import sound_recorder as sr
import quick_transcribe_audio as tr

app = Flask(__name__)

@app.route("/")
def main():
    return render_template('main.html')

@app.route("/record.do")
def record():
    prefix = './data/'
    suffix = '.wav'
    
    FILENAME = sr.file_name_generator(prefix=prefix, suffix=suffix)
    sr.sound_recording(FILENAME=FILENAME, RECORD_SECONDS=30)

    return "Sound recorded"

@app.route("/upload.do")
def goUpload():
    return render_template('upload.html')

@app.route("/transcribe1.do")
def transcribe1():
    FILENAME = './data/221121_1531.wav'
    tr.run_quickstart(FILENAME=FILENAME)

    return "Transcribe1 Done"

@app.route("/transcribe2.do")
def transcribe2():
    FILENAME = './data/221121_1318.wav'
    tr.run_quickstart(FILENAME=FILENAME)

    return "Transcribe2 Done"

if __name__ == "__main__":
    app.run()