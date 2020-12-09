<!DOCTYPE html>
<html>
<head>
    <title>Show countries</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<script>
    function hrefOfAddCountry() {
        document.getElementById("add").href = "/hazelcast/add/" + document.getElementById("newName").value;
    }

    function hrefOfFindByName() {
        document.getElementById("find").href = "/hazelcast/find/" + document.getElementById("name").value;
    }


</script>

<body>

<#--Link Menu-->
<table border="0"  >
<#--    <tr><th align="left">    <a style="color: green" href="/hazelcast-client-server">Go Back</a> </th></tr>-->
    <tr><th align="left" >    <a style="color: blue" href="person-list">Person List</a> </th></tr>
    <tr><th align="left" >    <a style="color: blue" href="person-insert">Add Person</a> </th></tr>
    <tr><th align="left" >    <a style="color: purple" href="fico-scoring-list">FicoScoring List</a> </th></tr>
</table>
<#if message?? >${message} ${seconds}, ${nanoSeconds}</#if>
<br><br>

<table border="1">
    <tr><td align="center" style="binding: none;background-color: grey;color: darkred; font-size: medium;font-weight: bold" colspan="6"> Fico Scoring List </td></tr>
    <tr>
<#--        <th>First Name</th>-->
<#--        <th>Last Name</th>-->
<#--        <th>Identity Code</th>-->
        <th>Payment History</th>
        <th>Amounts Owed</th>
        <th>Length Of CreditHistory</th>
        <th>New Credit</th>
        <th>Credit Mix</th>
        <th>Score</th>
    </tr>
    <#list ficoScoringList as f>
        <tr>
<#--            <td>${f.person.firstName}</td>-->
<#--            <td>${f.person.lastName}</td>-->
            <td>${f.paymentHistory}</td>
            <td>${f.amountsOwed}</td>
            <td>${f.lengthOfCreditHistory}</td>
            <td>${f.newCredit}</td>
            <td>${f.creditMix}</td>
            <td>${f.score}</td>
        </tr>
    </#list>
</table>


</body>
</html>