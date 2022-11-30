# STT 작동
def run_quickstart(FILENAME):
    # [START speech_quickstart]
    import io
    import os

    # Imports the Google Cloud client library
    # [START speech_python_migration_imports]
    from google.cloud import speech

    # [END speech_python_migration_imports]

    # Instantiates a client
    # [START speech_python_migration_client]
    client = speech.SpeechClient()
    # [END speech_python_migration_client]

    # The name of the audio file to transcribe
    #file_name = os.path.join(os.path.dirname(__file__), ".", "file.wav")

    # Loads the audio into memory
    with io.open(FILENAME, "rb") as audio_file:
        content = audio_file.read()
    
    audio = speech.RecognitionAudio(content=content)

    config = speech.RecognitionConfig(
        encoding=speech.RecognitionConfig.AudioEncoding.LINEAR16,
        sample_rate_hertz=16000,
        language_code="ko-KR",
    )

    # Detects speech in the audio file
    response = client.recognize(config=config, audio=audio)

    for result in response.results:
        print(u"Transcript: {}".format(result.alternatives[0].transcript))
        print(u'Confidence: {}'.format(result.alternatives[0].confidence))
    # [END speech_quickstart]