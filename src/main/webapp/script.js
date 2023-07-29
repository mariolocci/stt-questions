var SpeechRecognition = SpeechRecognition || webkitSpeechRecognition;
var SpeechGrammarList = SpeechGrammarList || webkitSpeechGrammarList;
var SpeechRecognitionEvent = SpeechRecognitionEvent || webkitSpeechRecognitionEvent;



var phrasePara = document.querySelector('.phrase');
var resultPara = document.querySelector('.result');
var diagnosticPara = document.querySelector('.output');
var answerPara = document.querySelector('.answer');

var testBtn = document.querySelector('button');

function httpGetAsync(theUrl, callback)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
    xmlHttp.open("GET", theUrl, true); // true for asynchronous 
    xmlHttp.send(null);
}
function testSpeech() {
  testBtn.disabled = true;
  testBtn.textContent = 'Test in progress';

  resultPara.textContent = 'result';
  resultPara.style.background = 'rgba(0,0,0,0.2)';
  diagnosticPara.textContent = '...diagnostic messages';
  
  var commands=[ 'accendi','spegni','luce','frigorifero'];
  var grammar = '#JSGF V1.0; grammar phrase; public <command> = ' + commands.join(' | ') + ' ;'
  var recognition = new SpeechRecognition();
  var speechRecognitionList = new SpeechGrammarList();
  speechRecognitionList.addFromString(grammar, 1);
  recognition.grammars = speechRecognitionList;
  recognition.lang = 'it-IT';
  recognition.interimResults = false;
  recognition.maxAlternatives = 1;

  recognition.start();

  recognition.onresult = function(event) {
    // The SpeechRecognitionEvent results property returns a SpeechRecognitionResultList object
    // The SpeechRecognitionResultList object contains SpeechRecognitionResult objects.
    // It has a getter so it can be accessed like an array
    // The first [0] returns the SpeechRecognitionResult at position 0.
    // Each SpeechRecognitionResult object contains SpeechRecognitionAlternative objects that contain individual results.
    // These also have getters so they can be accessed like arrays.
    // The second [0] returns the SpeechRecognitionAlternative at position 0.
    // We then return the transcript property of the SpeechRecognitionAlternative object 
    var speechResult = event.results[0][0].transcript;
    diagnosticPara.textContent = 'Speech received: ' + speechResult + '.';
    resultPara.textContent = speechResult;
    resultPara.style.background = 'lime';
    speechToText(speechResult)

    console.log('Confidence: ' + event.results[0][0].confidence);
  }

  recognition.onspeechend = function() {
    recognition.stop();
    testBtn.disabled = false;
    testBtn.textContent = 'Start new test';
  }

  recognition.onerror = function(event) {
    testBtn.disabled = false;
    testBtn.textContent = 'Start new test';
    diagnosticPara.textContent = 'Error occurred in recognition: ' + event.error;
  }

}

speechToText= function(question){
	callback= function(text){
		var docs=JSON.parse(text);
		answerPara.textContent=docs[0].body;
		answerPara.style.background='lime'
	}
	httpGetAsync("rest/semtech/searchText?text="+question, callback)
}
testBtn.addEventListener('click', testSpeech);
//resultPara.addEventListener('change',speechToText)