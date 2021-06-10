<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="/WEB-INF/ResourceBundleUTF.tld"%>
<%@taglib prefix="log" uri="http://logging.apache.org/log4j/tld/log"%>
<log:setLogger logger="indexJsp" var="indexJsp"/>
<log:log level="info" logger="${indexJsp}" message="Profile jsp has been visited"/>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang :pageContext.request.locale.language}" scope="session" />
<fmt:setLocale name="${lang}" />
<fmt:setBundle basename="resources" />
<!DOCTYPE html>
<html lang="${lang}">
<head>
    <title>Title</title>
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
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<div id="q-app">
    <div>
        <%@include file="../fichiers/headerHome.jsp"%>
    </div>
    <div class="q-pa-md row justify-begin">
        <div class="col-6">
            <q-card  class="q-ma-md col">
                <q-card-section class="text-center text-h5 q-pt-none">
                    <fmt:localeValue key="profileInfo"/>
                </q-card-section>
            </q-card>
            <q-card  class="q-ma-md col">
                <q-card-section class="text-center bg-primary text-white">
                   <fmt:localeValue key="dialogFirstName"/>
                </q-card-section>
                <q-separator></q-separator>
                <q-card-section class="text-center text-h5 q-pt-none">
                   ${requestScope.profileFirstName}
                </q-card-section>
                <q-card-section class="text-center bg-primary text-white">
                    <fmt:localeValue key="dialogSurName"/>
                </q-card-section>
                <q-separator></q-separator>
                <q-card-section class="text-center text-h5 q-pt-none">
                    ${requestScope.profileSurName}
                </q-card-section>
                <q-card-section class="text-center bg-primary text-white">
                    <fmt:localeValue key="profileRegistrationDate"/>
                </q-card-section>
                <q-separator></q-separator>
                <q-card-section class="text-center text-h5 q-pt-none">
                    ${requestScope.profileRegistrationDate}
                </q-card-section>
                <q-card-section class="text-center bg-primary text-white">
                    <fmt:localeValue key="profileRegistrationDate"/>
                </q-card-section>
                <q-separator></q-separator>
                <q-card-section class="text-center text-h5 q-pt-none">
                    ${requestScope.profileRegistrationDate}
                </q-card-section>
                <q-card-section class="text-center bg-primary text-white">
                    <fmt:localeValue key="profileAccountBalance"/>
                </q-card-section>
                <q-separator></q-separator>
                <q-card-section class="text-center text-h5 q-pt-none">
                    ${requestScope.profileAccountBalance}
                </q-card-section>

                <q-btn label="<fmt:localeValue key="addMoneyToAccount"/>" @click="addMoney()"></q-btn>
            </q-card>

        </div>

    </div>
</div>
</body>
<script>
    new Vue({
        el: '#q-app',
        data () {
            return {
                Eng:'',
                Ua:'',
                Ru:''

            }
        },
        methods:{
            goHome(){
                window.location.replace('${pageContext.request.contextPath}'+'/pages/index?lang=${sessionScope.lang}');
            },
            registrationPage(){
                window.location.href ='${pageContext.request.contextPath}'+'/pages/guest/registration?lang=${sessionScope.lang}';

            },
            addMoney(){
                window.location.href ='${pageContext.request.contextPath}'+'/pages/user/addMoney?lang=${sessionScope.lang}';
            },
            profilePage(){
                window.location.href ='${pageContext.request.contextPath}'+'/pages/user/profile?lang=${sessionScope.lang}';
            },
            makeOrder(){
                window.location.href='${pageContext.request.contextPath}'+'/pages/user/order?lang=${sessionScope.lang}';
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
