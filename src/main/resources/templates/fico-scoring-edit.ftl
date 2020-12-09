<!DOCTYPE html>
<html>
<head>
    <title>Show countries</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<script>

    function removeCommaDelimiters(){
        document.getElementById('paymentHistory').value = (document.getElementById('paymentHistory').value.replace(",",""));
        document.getElementById('amountsOwed').value = (document.getElementById('amountsOwed').value.replace(",",""));
        document.getElementById('lengthOfCreditHistory').value = (document.getElementById('lengthOfCreditHistory').value.replace(",",""));
        document.getElementById('newCredit').value = (document.getElementById('newCredit').value.replace(",",""));
        document.getElementById('creditMix').value = (document.getElementById('creditMix').value.replace(",",""));
        document.getElementById('score').value = (document.getElementById('score').value.replace(",",""));
        document.getElementById('score').value = (document.getElementById('score').value.replace(",",""));
        document.getElementById('person.id').value = (document.getElementById('person.id').value.replace(",",""));
    }



</script>

<body onload="removeCommaDelimiters()" >

<#--Link Menu-->
<table border="0"  >
<#--    <tr><th align="left">    <a style="color: green" href="/hazelcast-client-server">Go Back</a> </th></tr>-->
    <tr><th align="left" >    <a style="color: blue" href="/hazelcast-client-server/person-list">Person List</a> </th></tr>
    <tr><th align="left" >    <a style="color: blue" href="/hazelcast-client-server/person-insert">Add Person</a> </th></tr>
    <tr><th align="left" >    <a style="color: purple" href="/hazelcast-client-server/fico-scoring-list">FicoScoring List</a> </th></tr>
</table>
<#if message?? >${message} ${seconds}, ${nanoSeconds}</#if>
<br><br>
<form action="/hazelcast-client-server/fico-scoring-update" method="post" modelAttribute="ficoScoring">
    <table>
        <tr><td align="center" style="binding: none;background-color: grey;color: darkred; font-size: medium;font-weight: bold" colspan="2">
                <#if ficoScoring?? && ficoScoring.id??> Update Fico Scoring  <#else> Add Fico Scoring </#if>
            </td></tr>
        <tr><td></td></tr>
        <tr>
            <th style="text-align: right" >Payment History:</th>
            <td style="text-align: right" ><input type="text" name="paymentHistory" id="paymentHistory"  <#if ficoScoring?? && ficoScoring.id??> value="${ficoScoring.paymentHistory}" </#if> ></td>
        </tr>
        <tr>
            <th style="text-align: right" >Amounts Owed:</th>
            <td style="text-align: right" ><input type="text" name="amountsOwed" id="amountsOwed" <#if ficoScoring?? && ficoScoring.id??>  value="${ficoScoring.amountsOwed}" </#if> >  </td>
        </tr>
        <tr>
            <th style="text-align: right">Length Of CreditHistory:</th>
            <td style="text-align: right" ><input type="text" name="lengthOfCreditHistory" id="lengthOfCreditHistory" <#if  ficoScoring?? && ficoScoring.id??>  value="${ficoScoring.lengthOfCreditHistory}" </#if> > </td>
        </tr>
        <tr>
            <th style="text-align: right" >New Credit:</th>
            <td style="text-align: right" ><input type="text" name="newCredit" id="newCredit" <#if ficoScoring?? && ficoScoring.id??>  value="${ficoScoring.newCredit}" </#if> ></td>
        </tr>
        <tr>
            <th style="text-align: right" >Credit Mix:</th>
            <td style="text-align: right" ><input type="text" name="creditMix"  id="creditMix" <#if ficoScoring?? && ficoScoring.id??>  value="${ficoScoring.creditMix}" </#if> ></td>
        </tr>
        <tr>
            <th style="text-align: right" >Score:</th>
            <td style="text-align: right" ><input type="text" name="score" id="score"<#if ficoScoring?? && ficoScoring.id??>  value="${ficoScoring.score}" </#if> ></td>
        </tr>
        <tr hidden="true" >
            <th style="text-align: right">Id:</th>
            <td style="text-align: right"><input type="text" name="id" <#if ficoScoring?? && ficoScoring.id??>  value="${ficoScoring.id}" </#if> ></td>
        </tr>
        <tr  >
            <th style="text-align: right" >Person Id:</th>
            <td style="text-align: right" ><input type="text" name="person.id" id="person.id" <#if ficoScoring?? && ficoScoring.person??>  value="${ficoScoring.person.id}" </#if> ></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: right" ><input  type="submit" <#if ficoScoring?? && ficoScoring.id??>  value="Update" <#else>  value="Insert" </#if> > </td>
        </tr>
    </table>
</form>

</body>
</html>