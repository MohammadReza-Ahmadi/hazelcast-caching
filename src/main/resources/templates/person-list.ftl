<!DOCTYPE html>
<html>
<head>
    <title>Show countries</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<script>


    function removeCommaDelimiters(personId) {
        var url = (document.getElementById(personId).getAttribute('href')).replace(",","");
        document.getElementById(personId).setAttribute('href', url);
    }



</script>

<body>

<#--Link Menu-->
<table border="0">
    <#--    <tr><th align="left">    <a style="color: green" href="/hazelcast-client-server">Go Back</a> </th></tr>-->
    <tr>
        <th align="left"><a style="color: blue" href="person-list">Person List</a></th>
    </tr>
    <tr>
        <th align="left"><a style="color: blue" href="person-insert">Add Person</a></th>
    </tr>
    <tr>
        <th align="left"><a style="color: purple" href="fico-scoring-list">FicoScoring List</a></th>
    </tr>
</table>
<#if message?? >${message} ${seconds}, ${nanoSeconds}</#if>
<br><br>
<table border="1">
    <tr>
        <td align="center"
            style="binding: none;background-color: grey;color: darkred; font-size: medium;font-weight: bold"
            colspan="8"> Person List
        </td>
    </tr>
    <tr>
        <th>Identity Code</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Mobile</th>
        <th>Address</th>
        <th>Account Number</th>
        <th>Score</th>
        <th>......</th>
    </tr>
    <#list personList as p>
        <tr>
            <td>${p.identityCode}</td>
            <td>${p.firstName}</td>
            <td>${p.lastName}</td>
            <td>${p.mobile}</td>
            <td>${p.address}</td>
            <td>${p.accountNumber}</td>
            <td>
                <#if p.ficoScoring?? >
                    ${p.ficoScoring.score}
                    <#assign ficoScoringId=p.ficoScoring.id />
                <#else>
                    -1
                    <#assign ficoScoringId=-1 />
                </#if>
            </td>
            <td>
                <a id="${p.id}" onclick="removeCommaDelimiters('${p.id}')" href="fico-scoring-edit/${p.id}/${ficoScoringId}">Edit Score</a>
            </td>
        </tr>
    </#list>
</table>
ficoScoringId


</body>
</html>