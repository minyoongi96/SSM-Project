# 파일명 생성
def file_name_generator(prefix, suffix):
    from datetime import datetime
    
    cur = datetime.now()
    file_name = ''.join(list(map(str, [str(cur.year)[2:], cur.month, cur.day]))) + '_' + ''.join(list(map(str, [cur.hour, cur.minute])))
    
    return prefix + file_name + suffix

# 녹화
def sound_recording(FILENAME, RECORD_SECONDS):
    import pyaudio
    import wave

    FORMAT = pyaudio.paInt16
    CHANNELS = 1  #only mono
    RATE = 16000
    CHUNK = 1024  #확인 필요
    RECORD_SECONDS = RECORD_SECONDS #녹음 시간
    WAVE_OUTPUT_FILENAME = FILENAME

    audio = pyaudio.PyAudio()

    # start Recording
    stream = audio.open(format=FORMAT, channels=CHANNELS,
                    rate=RATE, input=True,
                    frames_per_buffer=CHUNK)
    print ("recording...")
    frames = []

    for i in range(0, int(RATE / CHUNK * RECORD_SECONDS)):
        data = stream.read(CHUNK)
        frames.append(data)
    print ("finished recording")


    # stop Recording
    stream.stop_stream()
    stream.close()
    audio.terminate()

    waveFile = wave.open(WAVE_OUTPUT_FILENAME, 'wb')
    waveFile.setnchannels(CHANNELS)
    waveFile.setsampwidth(audio.get_sample_size(FORMAT))
    waveFile.setframerate(RATE)
    waveFile.writeframes(b''.join(frames))
    waveFile.close()
