

function generateHeader() {

    var radios = document.getElementsByName('Causative');
    var type = "";
    if (radios[0].checked) {
        type = "non-causative";
    }
    else if (radios[1].checked) {
        type = "causative";
    }


    var tableID = "outputTable";
    var table = document.getElementById(tableID);
    var rowCount = table.rows.length;
    //var row = table.insertRow(rowCount);

    var verb = "";
    var subject = "";
    var object = "";
    for (x = 0; x < rowCount; x++) {
        var label = table.rows[x].cells[2].innerHTML;

        if (label == "Object") {
            object = table.rows[x].cells[3].innerHTML;
        }

        if (label == "Subject") {
            subject = table.rows[x].cells[3].innerHTML;
        }

        if (label == "Verbo Causativa") {
            verb = table.rows[x].cells[3].innerHTML;
        }
    }


    var sentid = document.getElementById("idfield").innerHTML;


    var headers = "category=" + type + "&subject=" + subject;
    headers = headers + "&verb=" + verb + "&object=" + object;
    headers = headers + "&sentid=" + sentid;
    return headers;



}


function loadXMLDoc()
{
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function()
    {

        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {

            //alert(xmlhttp.responseText);
            if (xmlhttp.responseText.length > 10) {
                var tmp = xmlhttp.responseText;
                var res = tmp.split("###");

                document.getElementById("progmenu").innerHTML
                        = res[0];
                document.getElementById("idfield").innerHTML = res[1];

            }

        }
    }

    var headers = generateHeader();
    xmlhttp.open("POST", "Label", true);
    var text = document.getElementById("progmenu").innerHTML;



    if (text.length <= 10) {
        xmlhttp.send();
    }
    else
    {
        xmlhttp.setRequestHeader("Content-Type",
                "application/x-www-form-urlencoded");
        xmlhttp.send(headers);
    }
}

function checkTable(selection) {
    errorMessage = "Ja existe uma seleção do: " + selection;
    tableID = "outputTable";
    var table = document.getElementById(tableID);
    cells = table.getElementsByTagName('td');
    for (var i = 0, len = cells.length; i < len; i++) {
        if (selection == cells[i].innerHTML) {
            alert(errorMessage);
            exit();
        }
    }
}

function writeText(selection) {

    checkTable(selection);
    //Enforce check boxes
    var radios = document.getElementsByName('Causative');
    for (var i = 0, length = radios.length; i < length; i++) {
        if (radios[i].value != "non") {
            radios[i].checked = true;
            break;
        }
    }

    var word = getSelectedText();
    addRow(word, selection);
    hideButton();
}

function hideButton() {

    tableID = "outputTable"
    var table = document.getElementById(tableID);
    var rowCount = table.rows.length;
    var b = document.getElementById("apagar");
    if (rowCount == 0) {
        b.hidden = true;
    }
    else {
        b.hidden = false;
    }

}


function getSelectedText() {

    var message = document.getElementById('progmenu');
    var selectedText = message.value.substring(message.selectionStart, message.selectionEnd);
    return selectedText;
}


function addRow(text, type) {
    tableID = "outputTable"
    var table = document.getElementById(tableID);

    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);

    var cell1 = row.insertCell(0);
    var element1 = document.createElement("input");
    element1.type = "checkbox";
    element1.name = "chkbox[]";
    cell1.appendChild(element1);

    var cell2 = row.insertCell(1);
    var tmpStr = "<input type=\"hidden\" name=\"rowid\"";
    tmpStr = tmpStr + "value=\"" + (rowCount + 1) + "\">";

    cell2.innerHTML = tmpStr;
    var cell3 = row.insertCell(2);
    cell3.innerHTML = type;

    var cell4 = row.insertCell(3);
    cell4.innerHTML = text;

}

function deleteRow() {
    try {
        tableID = "outputTable"
        var table = document.getElementById(tableID);
        var rowCount = table.rows.length;

        for (var i = 0; i < rowCount; i++) {
            var row = table.rows[i];
            var chkbox = row.cells[0].childNodes[0];
            if (null != chkbox && true == chkbox.checked) {
                table.deleteRow(i);
                rowCount--;
                i--;
            }

            hideButton();
        }
    } catch (e) {
        alert(e);
    }
}
function nextDocument() {
    /*check logic of save no annotations for non causative
     * annotations must be with causative
     */



    tableID = "outputTable";
    var table = document.getElementById(tableID);
    var rowCount = table.rows.length;

    var radios = document.getElementsByName('Causative');
    if (radios[0].checked == true && rowCount > 0) {
        errorMessage = "Frase não é causais não pode ter elementos causais. ";
        errorMessage = errorMessage + "Apaga os elementos causais na tabela ";
        alert(errorMessage);
        exit();
    }
    else if (radios[1].checked == true && rowCount == 0) {
        errorMessage = "Frase causais deve ter elementos causais. ";
        alert(errorMessage);
        exit();
    }

    else if (radios[1].checked == true && rowCount < 3) {
        errorMessage = "Frase causais deve ter 3 elementos causais. ";
        alert(errorMessage);
        exit();
    }

    loadXMLDoc();
    table.innerHTML = "";
}







