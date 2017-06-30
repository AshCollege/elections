/**
 * Created by Sigal on 5/21/2016.
 */
$(document).ready(function () {

    $(".payButton").click(function () {
        var total = $(this).attr("total");
        var invoiceId = $(this).attr("invoiceId");
        var totalInAgorot = total * 100;
        window.location = "/payment-request?total=" + totalInAgorot + "&&invoiceId=" + invoiceId;

    });


    $("#addNewClientButton").click(function () {

        var name = $("#clientName").val();
        // var num = $("#clientOid").val();
        var terminal = $("#clientTerminal").val();
        var fullName = $("#clientFullName").val();
        var address = $("#clientAddress").val();
        var zip = $("#clientZipCode").val();
        var HP = $("#clientHP").val();
        var telephone = $("#clientPhoneNumber").val();
        var fax = $("#clientFaxNumber").val();
        var mail = $("#clientMail").val();

        $.post("/add-new-client.json?", "name=" + name + "&&terminal=" + terminal + "&&fullName=" + fullName
            + "&&address=" + address + "&&zipCode=" + zip + "&&hp=" + HP + "&&telephoneNum=" + telephone + "&&faxNum=" + fax
            + "&&mail=" + mail, addNewClientResponse);
    });


    function addNewClientResponse(data, status) {
        var element = "";
        if (data.error) {
            element = "#addedClientFailure";
        } else {
            $("input").val("");
            element = "#addedClientSuccess";
        }
        fadeInOutElement(element);
    }


    // $("#showInvoices").change(function () {
    //     var showParameter = $(this).val();
    //
    //     if (showParameter == "all") {
    //         $(".paid").fadeIn("slow");
    //         $(".unpaid").fadeIn("slow");
    //
    //     }
    //     else if (showParameter == "paid") {
    //         $(".unpaid").fadeOut("slow");
    //         $(".paid").fadeIn("slow");
    //     }
    //     else if (showParameter == "unpaid") {
    //         $(".paid").fadeOut("slow");
    //         $(".unpaid").fadeIn("slow");
    //     }
    //
    // });

    $("#chooseYear").change(function () {
        var chosenYear = $(this).val();

        window.location = "/consumpsion-report?year=" + chosenYear;
    });

    $("#searchInvoices").click(function () {

        var lowInvoiceNum = $("#searchLowInvoiceNum").val();
        var highInvoiceNum = $("#searchHighInvoiceNum").val();
        var lowConsumerUid = $("#searchLowConsumerUid").val();
        var highConsumerUid = $("#searchHighConsumerUid").val();
        var lowTotal = $("#searchLowTotal").val();
        var highTotal = $("#searchHighTotal").val();
        var lowDate = $("#searchLowDate").val();
        var highDate = $("#searchHighDate").val();
        var lowRun = $("#searchLowRun").val();
        var highRun = $("#searchHighRun").val();
        var month = $("#searchMonth").val();
        var year = $("#searchYear").val();
        var payStatus = $("#searchStatus").val();
        var paymentConfirmation = $("#searchPaymentConfirmation").val();


        $.post("/search-invoices.json?", "lowInvoiceNum=" + lowInvoiceNum + "&&highInvoiceNum=" + highInvoiceNum +
            "&&lowConsumerUid=" + lowConsumerUid + "&&highConsumerUid=" + highConsumerUid +
            "&&lowTotal=" + lowTotal +  "&&highTotal=" + highTotal +
            "&&lowDate=" + lowDate + "&&highDate=" + highDate +
            "&&lowRun=" + lowRun + "&&highRun=" + highRun +
            "&&month=" + month + "&&year=" + year + "&&payStatus=" + payStatus +"&&paymentConfirmation=" + paymentConfirmation, searchInvoicesResponse);
    });

    function searchInvoicesResponse(data, error) {
        var invoicesList = data.invoicesOidList;
        var allInvoices = data.allInvoices;
      //  var resultsNum = data.resultsNum;

        $("#allInvoicesTable").show();
        $(".invoice-row").hide();
        $("#no-matching-results").hide();

        $("#resultsNum").text(invoicesList.length + " " +translationsMap["search.results"]);//invoicesList.length);

        $("#resultsNum").show();


        for (var i = 0; i < invoicesList.length; i++) {
            var oid = invoicesList[i];
            var idToShow = "#invoiceId-" + oid;
            $(idToShow).show();
        }
        if(invoicesList.length==0){

            $("#allInvoicesTable").hide();
            $("#no-matching-results").show();
            $("#resultsNum").hide();
        }

        //  $("#searchResultTable").append('#foreach ($invoice in ' + invoicesList +') <tr> <td> $invoice.oid</td></tr>#end');
    }

    //function chooseYearResponse(data,status) {
    //   var year = data.chosenYear;
    //  $("#yearNum"+year).attr("")
    // window.location="/consumpsion-report?year=" + year;

//    }

    $(".clients-dropdown-item").click(function () {
        var oid = $(this).attr("client-oid");
        $("#client").val($(this).text());
        $("#clientOid").val(oid);
    });


    function newSupervisorResponse(data, status) {
        alert(data.error ? "Failure" : "Success");
    }

    function fadeInOutElement(element) {
        $(element).fadeIn(1000).delay(1000).fadeOut(4000);
    }

    function fadeInElement(element) {
        $(element).fadeIn(1000);
    }

    function fadeOutElement(element) {
        $(element).fadeOut(4000);
    }

    $(".edit-client-details").click(function () {
        var clientOid = $(this).attr("client-oid");
        $(".client-details input").attr("disabled", true);
        if (($(this).text()) === translationsMap["save"]) {
            $(".edit-client-details").text(translationsMap["edit"]);
            var clientName = $("#clientName-" + clientOid).val();
            var clientTerminal = $("#clientTerminal-" + clientOid).val();
            var clientAddress = $("#clientAddress-" + clientOid).val();
            var clientZipCode = $("#clientZipCode-" + clientOid).val();
            var clientFullName = $("#clientFullName-" + clientOid).val();
            var clientHp = $("#clientHp-" + clientOid).val();
            var clientTelephoneNum = $("#clientTelephoneNum-" + clientOid).val();
            var clientFaxNum = $("#clientFaxNum-" + clientOid).val();
            var clientMail = $("#clientMail-" + clientOid).val();
            var clientShow = $("#clientShow-" + clientOid).is(":checked");
            $.post("/update-client-details.json?", "oid=" + clientOid + "&&name=" + clientName + "&&terminal=" + clientTerminal + "&&address=" + clientAddress
                + "&&zipCode=" + clientZipCode + "&&fullName=" + clientFullName + "&&hp=" + clientHp + "&&telephoneNum=" + clientTelephoneNum
                + "&&faxNum=" + clientFaxNum + "&&email=" + clientMail + "&&show=" + clientShow, updateClientDetailsResponse);

        } else {
            $("tr[client-oid=" + clientOid + "] input").removeAttr('disabled');
            $(this).text(translationsMap["save"]);
        }
        $(".uneditable").attr("disabled", true);
    });

    function updateClientDetailsResponse(data, status) {
        if (data.error) {
            alert("Error saving client details");
        }
    }


    $("#manageExistingClientsButton").click(function () {
        if ($("#manageExistingClientsTable").is(':visible')) {
            $("#manageExistingClientsTable").hide("slow");
            $(this).text(translationsMap["show.existing.clients"]);
        } else {
            $("#manageExistingClientsTable").show("slow");
            $(this).text(translationsMap["hide.existing.clients"]);
        }
    });

    $("#addNewClientFormButton").click(function () {
        if ($("#addNewClientForm").is(':visible')) {
            $("#addNewClientForm").hide("slow");
            $(this).text(translationsMap["show.add.new.client.form"]);
        } else {
            $("#addNewClientForm").show("slow");
            $(this).text(translationsMap["hide.add.new.client.form"]);
        }
    });

    $("#importButton").click(function () {
        if ($("#importFile").is(':visible')) {
            $("#importFile").hide("slow");
            $(this).text(translationsMap["show.import"]);
        } else {
            $("#importFile").show("slow");
            $(this).text(translationsMap["hide.import"]);
        }
    });

    $("#generalButton").click(function () {
        if ($("#generalTable").is(':visible')) {
            $("#generalTable").hide("slow");
            $(this).text(translationsMap["show.general"]);
        } else {
            $("#generalTable").show("slow");
            $(this).text(translationsMap["hide.general"]);
        }
    });

    $("#changeLang").change(function () {
        var selectedLang = $('#changeLang').find(":selected").val();
        $.post("/change-lang.json?", "langCode=" + selectedLang, changeLangResponse);
    });

    function changeLangResponse(data, status) {
        if (!data.error) {
            location.reload();
        } else {
            alert("שגיאה בשינוי שפה")
        }
    }

});

