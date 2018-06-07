window.onload = function () {

    var makeTableButton = $("#makeTableButton");
    var updateButton = $("#updateButton");
    var downloadButton = $("#downloadFullTable");
    var rep1Button = $("#downloadReport1");
    var popup = $("#popup_form");
    var popupHeader = $("#popup_menu_header");
    var submit = $("#submit");
    var closeButton = $(".alert-close");
    var currentDate = $("#currentDate");
    var otchetDate = $("#otchetDate");
    var infoDiv = $("#info_div");
    var blockDiv = $("#block_div");
    $('.calendar').MonthPicker({MaxMonth: 1}, {Button: false});
    popup.hide();

    updateStatusInfo();

    //calls servlet to get information about last table update
    function updateStatusInfo() {
        sendAjaxToServlet("./getStatus",
            "GET",
            null,
            null,
            processUpdateStatus,
            propertyError
        );
    }

    //processes propertyHandler response and changes html according to it
    function processUpdateStatus(data) {
        var stat = data.property;
        if (stat == "0") {
            infoDiv.text("Информация о последнем обновлении отсутствует");
            blockDiv.hide();
        }
        else if (stat == "error") {
            infoDiv.text("Последняя попытка обновления завершилась ошибкой");
            blockDiv.hide();
        }
        else {
            infoDiv.text("Последнее обновление завершилось " + stat);
            blockDiv.hide();
        }
    }

    function propertyError(data) {
        infoDiv.text("Не удалось получить информацию о последнем обновлении");
    }

    makeTableButton.click(function () {
        showPopupForm("remakeTable", "Формирование таблицы")
    });
    updateButton.click(function () {
        showPopupForm("updateTable", "Обновление таблицы")
    });
    downloadButton.click(function () {
        window.open("csv/pretenz")
    });
    closeButton.click(function () {
        $(this).parent().fadeOut("slow");
    });
    rep1Button.click(function () {
        window.open("csv/ReportView1");
    });

    //makes popup form visible and changes it according to which button has been pressed
    function showPopupForm(onClickFunction, header) {
        submit.click(function (e) {
            e.preventDefault();
            sendMakeTableCommand(onClickFunction);
            setTimeout(function () {
                window.location.reload();
            }, 60000);
        });
        popupHeader.text(header);
        popup.show();
    }

    //calls servlet to make/update db table
    function sendMakeTableCommand(typeOfPost) {
        if (currentDate.val() == "" || otchetDate.val() == "")
            alert("Не выбраны периоды");
        else {
            blockDiv.innerHTML + "Подождите. Идет обновление таблицы... ";
            blockDiv.show();
            var url = "";
            if (typeOfPost === "remakeTable")
                url = "./table/recreate";
            else if (typeOfPost === "updateTable")
                url = "./table/update";
            sendAjaxToServlet(
                url,
                "POST",
                null,
                {
                    currentMonth: currentDate.val().substring(0, 2),
                    currentYear: currentDate.val().substring(3, 8),
                    otchetMonth: otchetDate.val().substring(0, 2),
                    otchetYear: otchetDate.val().substring(3, 8)
                },
                done,
                function (xhr) {
                    infoDiv.text(xhr.responseText);
                    blockDiv.hide("slow");
                }
            );
        }
    }

    function done() {
        infoDiv.text("Обновление завершено");
        blockDiv.hide("slow");
        // updateStatusInfo();
    }

    function sendAjaxToServlet(url, method, headers, data, success, error) {
        $.ajax({
            url: url,
            type: method,
            headers: headers,
            dataType: "json",
            data: data
        }).done(success).fail(error)
    }
};