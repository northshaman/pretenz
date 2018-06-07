<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8" %>
<html>
<head>
    <%--<meta charset="UTF-8">--%>
    <title>Претензионный модуль</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/CSS/jquery-ui.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/CSS/master.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/CSS/MonthPicker.min.css">
    <script src="${pageContext.servletContext.contextPath}/JS/jquery-1.10.0.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/JS/jquery.maskedinput.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/JS/jquery-ui.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/JS/MonthPicker.min.js"></script>
    <!--<script src="http://proxy-gf.esbt.loc/lib/1.5/jquery.min.js"></script>-->


    <script src="${pageContext.servletContext.contextPath}/JS/index.js"></script>
</head>
<body>
<div class="mainwrapper">
    <div class="header">
        <h1>Претензионный модуль</h1>
    </div>
    <div class="panel mainmenu">
        <div class="simplediv"><h3 id="mainMenuHeader">Главное меню</h3></div>
        <button class="button" id="makeTableButton" title="Сформировать таблицу заново">Ежемесячное обновление таблицы
        </button>
        <button class="button" id="updateButton" title="Обновить данные из СС&B">Ежедневное обновление таблицы</button>
        <button class="button" id="downloadFullTable" title="Скачать таблицу">Скачать таблицу целиком
        </button>
        <button class="button" id="downloadReport1" title="Отчет №1">Скачать Отчет №1</button>
    </div>

    <div id="popup_form">
        <h4 id="popup_menu_header">Форма</h4>
        <div class="alert-close">X</div>
        <form>
            Текущий период:<br/>
            <input id="currentDate" class="calendar" type="text" placeholder="Текущий период"/> <br/>
            Отчетный период:<br/>
            <input id="otchetDate" class="calendar" type="text" placeholder="Отчетный период"/> <br/>
            <button id="submit" class="button" title="ОК">ОК</button>
        </form>
    </div>
</div>
<div id="info_div"></div>
<div id="block_div">
    <p><img id="lamp" border="0" src="${pageContext.servletContext.contextPath}/img/bombillita.gif"></p>
</div>


</body>
</html>
