function AutoSuggestControl(oTextbox) {
    this.layer = null;
    this.textbox = oTextbox;
    this.init();
    this.createDropDown();

    // send Google suggest request based on the user input
    this.sendAjaxRequest = function(xmlHttp, doTypeAhead)
    {
        var input = this.textbox.value;
        if(input != ""){
            var request = "suggest?q="+encodeURIComponent(input);
            xmlHttp.open("GET", request);
            var asControl = this;
            xmlHttp.onreadystatechange = function(){
                if (xmlHttp.readyState == 4) {
                    var suggList = [];
                    var suggestionsXML = xmlHttp.responseXML;
                    if(suggestionsXML != null){
                        var suggestions = suggestionsXML.getElementsByTagName('CompleteSuggestion');
                        for (i = 0; i < suggestions.length; i++) {
                            var text = suggestions[i].childNodes[0].getAttribute("data");
                            suggList.push(text);
                        }
                        asControl.autosuggest(suggList, doTypeAhead);
                    }
                }
            };
    /*        xmlHttp.onreadystatechange = parseSuggestions(xmlHttp, doTypeAhead);*/
            xmlHttp.send(null);
        } else{
            this.hideSuggestions();
        }
    }

    

    this.handleKeyUp = function (oEvent) {
        var xmlHttp = new XMLHttpRequest(); 
        var iKeyCode = oEvent.keyCode;
        if (iKeyCode == 8 || iKeyCode == 46) {
            this.sendAjaxRequest(xmlHttp, false);
        } else if (iKeyCode < 32 || (iKeyCode >= 33 && iKeyCode <= 46) || (iKeyCode >= 112 && iKeyCode <= 123)) {
            //ignore
        } else {
            this.sendAjaxRequest(xmlHttp, true);
        }
    }


    /*this.selectRange = function (iStart, iLength) {
        if (this.textbox.createTextRange) {
            var oRange = this.textbox.createTextRange(); 
            oRange.moveStart("character", iStart); 
            oRange.moveEnd("character", iLength - this.textbox.value.length); 
            oRange.select();
        } else if (this.textbox.setSelectionRange) {
            this.textbox.setSelectionRange(iStart, iLength);
        }
        this.textbox.focus(); 
    }

    this.typeAhead = function (sSuggestion) {
        if (this.textbox.createTextRange || this.textbox.setSelectionRange) {
            var iLen = this.textbox.value.length; 
            this.textbox.value = sSuggestion; 
            this.selectRange(iLen, sSuggestion.length);
        }
    }
*/
    this.autosuggest = function (suggestList, doTypeAhead) {
        if (suggestList.length > 0) {
           /* if(doTypeAhead)
                this.typeAhead(suggestList[0]);*/
            this.showSuggestions(suggestList);
        }
    }

    this.hideSuggestions = function () {
        this.layer.style.visibility = "hidden";
    }

    this.highlightSuggestion = function (currentlySelected) {
        for(var i = 0; i < this.layer.childNodes.length; i++){
            var option = this.layer.childNodes[i];
            if(option == currentlySelected)
                option.className = "current";
            else if(option.className == "current")
                option.className = "";
        }
    }

    this.getLeft = function () {
        var oNode = this.textbox;
        var iLeft = 0;

        while(oNode.tagName != "BODY") {
            iLeft += oNode.offsetLeft;
            oNode = oNode.offsetParent; 
        }

        return iLeft;
    }

    this.getTop = function () {
        var oNode = this.textbox;
        var iTop = 0;

        while(oNode.tagName != "BODY") {
            iTop += oNode.offsetTop;
            oNode = oNode.offsetParent; 
        }

        return iTop;
    }

    this.showSuggestions = function (aSuggestions) {
        var oDiv = null;
        this.layer.innerHTML = "";

        for (var i=0; i < aSuggestions.length; i++) {
            oDiv = document.createElement("div");
            oDiv.appendChild(document.createTextNode(aSuggestions[i]));
            this.layer.appendChild(oDiv);
        }

        this.layer.style.left = this.getLeft() + "px";
        this.layer.style.top = (this.getTop()+this.textbox.offsetHeight) + "px";
        this.layer.style.visibility = "visible";
        this.layer.style.background = "white";
    }
}

AutoSuggestControl.prototype.init = function () {
    var oThis = this;
    this.textbox.onkeyup = function (oEvent) {
        if (!oEvent) {
            oEvent = window.event;
        }
        oThis.handleKeyUp(oEvent);
    };
};

AutoSuggestControl.prototype.createDropDown = function(){
        this.layer = document.createElement("div");
        this.layer.className = "suggestions";
        this.layer.style.visibility = "hidden";
        this.layer.style.width = this.textbox.offsetWidth;
        document.body.appendChild(this.layer);

        var asControl = this;

        this.layer.onmousedown = this.layer.onmouseup =
        this.layer.onmouseover = function(event){
            event = event || window.event;
            target = event.target || event.srcElement;
            if(event.type == "mousedown"){
                asControl.textbox.value = target.firstChild.nodeValue;
                asControl.hideSuggestions();
            } else if (event.type == "mouseover"){
                asControl.highlightSuggestion(target);
            } else{
                asControl.textbox.focus(); //return focus to the text box
            }
        };
    };