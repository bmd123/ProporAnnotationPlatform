<%-- 
    Document   : Annotation
    Created on : Dec 10, 2013, 11:11:27 AM
    Author     : brett
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Anotação de frases causais.</title>
        <script>dojoConfig = {async: true}</script>
        <script>dojoConfig = {parseOnLoad: true}</script>
        <script src="http://ajax.googleapis.com/ajax/libs/dojo/1.9.1/dojo/dojo.js"></script>
        <script src="script.js"></script>
        <style src ="style.css"></style>

    </head>
    <body onload="loadXMLDoc()">
        
          <h2 align='center'> 
            <% br.usp.properties.Properties p = new br.usp.properties.Properties();
            %>   
          
            
          </h2>
        
        <a onclick="window.open('Instructions.html', 'newwindow', 'width=300, height=250'); return false;"> Instruções (Clique Aqui) </a>
        <br/>
        <div id='response'>
        </div> 

        <div>
            <form>
                <center>
                    <textarea id="progmenu" readonly="True" cols="50" rows ="10" > </textarea>
                </center>
                <input type="hidden" id ="idfield" name="idfield" value=" "/>
            </form>

        </div>


        <div id='annotation'>
            <center>

                <input type="radio" name="Causative" id="nc" checked value="non"/> <label for="radioOne">Não Causativa</label> <br/>
                <input type="radio" name="Causative" id="c" value="Causativa"/> <label for="radioTwo">Causativa</label>
                <br/>


                <table id="outputTable"></table>
                <button id="apagar" onclick ="deleteRow()" hidden="true">
                    Apaga Anotação
                </button>
            </center>
        </div>

        <div style="padding-top:10px" id='next' align='center'>
            <button type="button" onclick ="nextDocument();" 
                    id="submitButton">Seguinte</button>
        </div>
        
        

    </body>
</html>
