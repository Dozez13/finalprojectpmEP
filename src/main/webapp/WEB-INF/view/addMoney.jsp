<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="/WEB-INF/ResourceBundleUTF.tld"%>
<%@taglib prefix="log" uri="http://logging.apache.org/log4j/tld/log"%>
<%@taglib prefix="image"   tagdir="/WEB-INF/tags/imageHelper"%>

<log:setLogger logger="addmoney" var="addmoney"/>
<log:log level="info" logger="${addmoney}" message="Index jsp has been visited"/>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang :pageContext.request.locale.language}" scope="session" />
<fmt:setLocale name="${lang}" />
<fmt:setBundle basename="resources" />
<!DOCTYPE html>
<html lang="${lang}">
<head>
    <title>Add money</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,900|Material+Icons" rel="stylesheet" type="text/css">
    <link href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" rel="stylesheet" type="text/css">
    <link href="https://maxst.icons8.com/vue-static/landings/line-awesome/font-awesome-line-awesome/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://cdn.jsdelivr.net/npm/animate.css@^4.0.0/animate.min.css" rel="stylesheet" type="text/css">
    <link href="https://cdn.jsdelivr.net/npm/quasar@1.15.13/dist/quasar.min.css" rel="stylesheet" type="text/css">
    <script>
        window.quasarConfig = {
            brand: {
                primary: '#003cd4',
                secondary: '#26a679',
                accent: '#9C27B0',
                dark: '#1d1d1d',
                positive: '#e3ffc9',
                negative: '#C10015',
                info: '#32eda5',
                warning: '#ffbb00'
            }
        }
    </script>
</head>

<body>
<script src="https://cdn.jsdelivr.net/npm/vue@^2.0.0/dist/vue.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/quasar@1.15.13/dist/quasar.umd.modern.min.js"></script>
<div id="q-app">
    <div>
        <%@include file="../fichiers/headerHome.jsp"%>
    </div>
    <div class="q-pa-md q-gutter-sm">
        <q-card>
            <q-card-section class="text-center text-h5 q-pt-none">
                <fmt:localeValue key="addMoneyToAccountTitle"/>
            </q-card-section>
        </q-card>
        <q-form action="${pageContext.request.contextPath}/pages/user/addMoneyM" method="post">
        <q-input
                v-model="monNumber"
                label="<fmt:localeValue key="addMoneyAmount"/>"
                name="amountM"
                filled
                type="number"
                :hint="amountHint"
                :rules="[val => val !== null && val !== '' && val !== 0 || '<fmt:localeValue key="addMoneyAmountHint"/>']"
        >
        </q-input>
        <q-input
                filled
                v-model="card"
                name="cardNum"
                label="<fmt:localeValue key="addMoneyCardInput"/>"
                mask="4### #### #### ####"
                fill-mask="#"
                :hint="cardHint"
        ></q-input>
            <q-btn type="submit" label="<fmt:localeValue key="addMoneyFromCard"/>"></q-btn>
        </q-form>
    </div>
</div>
</body>
<script>
    new Vue({
        el: '#q-app',
        data (){
            return{
                Eng:'',
                Ua:'',
                Ru:'',
                monNumber:0,
                amountHint:'<fmt:localeValue key="addMoneyAmountHint"/>',
                card: null,
                cardHint:'<fmt:localeValue key="addMoneyCardInputHint"/>'
            }
        },
        methods:{
                goHome(){
                    window.location.replace('${pageContext.request.contextPath}'+'/pages/index?lang=${sessionScope.lang}');
                },
                registrationPage(){
                    window.location.href ='${pageContext.request.contextPath}'+'/pages/guest/registration?lang=${sessionScope.lang}';

                },
                profilePage(){
                    window.location.href ='${pageContext.request.contextPath}'+'/pages/user/profile?lang=${sessionScope.lang}';
                },
                makeOrder(){
                    window.location.href='${pageContext.request.contextPath}'+'/pages/user/order?lang=${sessionScope.lang}';
                },
                myOrders(){
                window.location.href='${pageContext.request.contextPath}'+'/pages/user/myOrders?lang=${sessionScope.lang}';
                },
                checkOrders(){
                    window.location.href='${pageContext.request.contextPath}'+'/pages/admin/orders?lang=${sessionScope.lang}';
                },
                changeToEng(){
                    window.location.href = window.location.href+'?lang=en'
                },
                changeToUa(){
                    window.location.href = window.location.href+'?lang=ua'
                },
                changeToRu(){
                    window.location.href = window.location.href+'?lang=ru'
                }

        },
        mounted: function(){
            window.history.pushState({}, document.title,window.location.href.split(/[?#]/)[0]);
            switch ('${sessionScope.lang}'){
                case 'en':{
                    this.Eng='black'
                    break
                }
                case 'ua':
                case 'uk_UA':{
                    this.Ua='black'
                    break
                }
                case 'ru':{
                    this.Ru='black'
                    break
                }
            }
        }
    })
</script>
</html>
