<%-- 
    Document   : index
    Created on : Nov 25, 2013, 11:35:32 AM
    Author     : posdoc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <script>

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

                            document.getElementById("response").innerHTML
                                    = xmlhttp.responseText;
                        }

                    }
                }
                xmlhttp.open("POST", "Data", true);
                text = document.getElementById("response").innerHTML;
                start = text.indexOf('<font size="2">') + '<font size="2">'.length;
                end = text.indexOf('</font>');
                phrase = text.substring(start, end);
                //alert(start);
                start = text.indexOf("value") + "value+\'".length;
                end = text.indexOf("type");
                ph = text.substring(start, (end - 2));

                text = document.getElementById("nc").innerHTML;

                var radios = document.getElementsByName('Causative');

                var non = "False";
                for (var i = 0, length = radios.length; i < length; i++) {
                    if (radios[i].checked) {

                        if (radios[i].value == "non") {
                            non = "True";
                            break;
                        }

                        break;
                    }
                }

                xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xmlhttp.send("text=" + phrase + "&id=" + ph + "&non=" + non);

            }
        </script>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Anotação de Relações Causais</title>
    </head>
    <body onload="loadXMLDoc()">


        <h2 align='center'>Anotação de Relaçoes Causais</h2>

        <p align='justify'> Estamos tentando criar um <i> benchmark </i> para 
            relações causais para a língua portuguesa. 
            A primeira parte é categorização das frases em duas categorias: 
            causativa ou não é causativa </p>

        <p align='justify'> A caixa vai apresentar frases aleatórias. </br>
            Selecione uma opção: 1. causativa ou 2. não causativa e clique o 
            botão "seguinte".
        </p>




        <div id="cause">
            <form id="ajaxform" id ="ajaxform" action='null'>
                <i> Frase </i>
                <center>
                    <div id="response" style="border:2px solid #a1a1a1;">

                    </div>
                    </br>

                    <input type="radio" name="Causative" id="nc" checked value="non"/> <label for="radioOne">Não Causativa</label> <br/>
                    <input type="radio" name="Causative" id="c" value="Causativa"/> <label for="radioTwo">Causativa</label>
                    <br/>


                    <button type="button" onclick ='loadXMLDoc()' 
                            id="submitButton">Seguinte</button>
                
                    <br/>
                    <br/>
                    <h3> Exemplos </h3>
                </center>
                
                <p>
                    <b> Frase causativa:</b> Excesso de chuva causa problemas para produtores de arroz do Rio Grande do Sul </br>
                    <br/>
                    <b> Frase não causativa:</b> o Brasil teria que se tornar um imenso canavial 
                </p>
            </form>
        </div>
    </body>
</html>
