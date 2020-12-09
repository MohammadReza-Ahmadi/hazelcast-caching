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

<form  action="person-add" method="post" modelAttribute="person" >
<table >
    <tr><td align="center" style="binding: none;background-color: grey;color: darkred; font-size: medium;font-weight: bold" colspan="2"> Add Person </td></tr>
    <tr><td></td></tr>
    <tr>
        <th style="text-align: right" >Identity Code: </th>
        <td style="text-align: right" ><input type="text" name="identityCode" ></td>
    </tr>
    <tr>
        <th style="text-align: right" >First Name: </th>
        <td style="text-align: right" ><input type="text" name="firstName" ></td>
    </tr>
    <tr>
        <th style="text-align: right">Last Name: </th>
        <td style="text-align: right"><input type="text" name="lastName" ></td>
    </tr>
    <tr>
        <th style="text-align: right">Mobile: </th>
        <td style="text-align: right" ><input type="text" name="mobile" ></td>
    </tr>
    <tr>
        <th style="text-align: right" >Address: </th>
        <td style="text-align: right" ><input type="text" name="address" ></td>
    </tr>
    <tr>
        <th style="text-align: right" >Account Number</th>
        <td style="text-align: right"><input type="text" name="accountNumber" ></td>
    </tr>
    <tr>
        <td colspan="2" style="text-align: right"><input type="submit" value="insert" ></td>
    </tr>
</table>
</form>

</body>
</html>