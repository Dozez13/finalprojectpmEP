<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/ResourceBundleUTF.tld"%>
<%@taglib prefix="log" uri="http://logging.apache.org/log4j/tld/log"%>
<log:setLogger logger="registrJsp" var="registrJsp"/>
<log:log level="info" logger="${registrJsp}" message="Registration jsp has been visited"/>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang :pageContext.request.locale}" scope="session" />
<fmt:setLocale name="${lang}" />
<fmt:setBundle basename="resources" />
<!DOCTYPE html>
<html lang="${lang}">
<head >
    <title>Registration</title>
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

<c:if test="${sessionScope.Login!=null}">
    <c:redirect url="/pages/guest/index"/>
</c:if>
<div id="q-app">
    <div>
        <%@include file="../fichiers/headerforregistration.jsp"%>
    </div>
    <div class="q-pa-md row justify-center">
        <div class="col-4">
            <q-card style="left: 15px" class="q-ma-md col-5">
                <q-card-section>
                    <div class="text-center text-h5"><fmt:localeValue key="registrationLabel"/></div>
                </q-card-section>
                <q-card-section class="text-center q-pt-none">
                    <fmt:localeValue key="registrationCreateAccount"/>
                </q-card-section>
            </q-card>
            <q-form action="${pageContext.request.contextPath}/pages/guest/doRegistration" method="post">
                <q-input
                        filled
                        debounce="300"
                        type="text"
                        :hint="firstNameHint"
                        v-model="firstName"
                        label="<fmt:localeValue key="dialogFirstName"/>"
                        name="firstName"
                        class="q-ma-md"
                        :rules="[ val => val.match(/(^([a-z-а-яА-ЯЇЄЁЭїєёэ]{5,25})$)/ig)!=null || '<fmt:localeValue key="loginValidation" />']"
                ></q-input>
                    <q-input
                            filled
                            debounce="300"
                            type="text"
                            :hint="surNameHint"
                            v-model="surName"
                            label="<fmt:localeValue key="dialogSurName"/>"
                            name="surName"
                            class="q-ma-md"
                            :rules="[ val => val.match(/(^([a-z-а-яА-ЯЇЄЁЭїєёэ]{5,25})$)/ig)!=null || '<fmt:localeValue key="loginValidation" />']"
                    ></q-input>
                <q-input
                        filled
                        debounce="300"
                        type="text"
                        :hint="LogHint"
                        v-model="login"
                        label="<fmt:localeValue key="dialogLogin"/>"
                        name="login"
                        class="q-ma-md"
                        :rules="[ val => val.match(/(^([a-z-а-яА-ЯЇЄЁЭїєёэ]{5,19})$)/ig)!=null || '<fmt:localeValue key="loginValidation" />']"
                >
                    <template v-slot:before>
                        <q-icon name="login"></q-icon>
                    </template>
                </q-input>
                <q-input
                        filled
                        debounce="300"
                        type="email"
                        :hint="EmailHint"
                        v-model="model"
                        label="<fmt:localeValue key="registrationEmail"/>"
                        name="Email"
                        class="q-ma-md"
                        :rules="[ val => val.match(/(^([a-z\d]{1,64}@[a-z\d-]{1,200}[\.][a-z]([a-z\d]{1,53}))$)/i)!=null || '<fmt:localeValue key="emailValidation"/>']"
                >

                    <template v-slot:before>
                        <q-icon name="mail"></q-icon>
                    </template>
                </q-input>
                <q-input v-model="password"
                         class="q-ma-md"
                         debounce="300"
                         name="psw"
                         label="<fmt:localeValue key="dialogPassword"/>"
                         :rules="[ val => val.match(/(^([\wа-яА-ЯЇЄЁЭїєёэ]{5,20})$)/ig)!=null || '<fmt:localeValue key="passwordValidation"/>']"
                         filled :type="isPwd ? 'password' : 'text'"
                         :hint="PasswordHint">
                    <template v-slot:before>
                        <q-icon
                                :name="isPwd ? 'visibility_off' : 'visibility'"
                                class="cursor-pointer"
                                @click="isPwd = !isPwd"
                        ></q-icon>
                    </template>
                </q-input>
                <q-btn class="q-ma-md" label="<fmt:localeValue key="registrationCreateAccount"/>" type="submit" color="primary"></q-btn>
            </q-form>
        </div>
    </div>
</div>
</body>
<script>
    new Vue({
        el: '#q-app',
        data () {
            return {
                model: '',
                firstName:'',
                surName:'',
                password: '',
                login:'',
                isPwd: true,
                LogHint:'<fmt:localeValue key="LogHintD"/>',
                EmailHint:'<fmt:localeValue key="EmailHint"/>',
                PasswordHint:'<fmt:localeValue key="PasswordHintD"/>',
                firstNameHint:'<fmt:localeValue key="firstNameHint"/>',
                surNameHint:'<fmt:localeValue key="surNameHint"/>',
                passwordD: '',
                loginD:'',
                isPwdD: true,
                LogHintD:'<fmt:localeValue key="LogHintD"/>',
                PasswordHintD:'<fmt:localeValue key="PasswordHintD"/>',
                showDialogD: false,
                Eng:'',
                Ua:'',
                Ru:''
            }
        },
        methods:{
            goHome(){
                window.location.replace('${pageContext.request.contextPath}'+'/pages/index?lang=${sessionScope.lang}');
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
            if('${param.registrationMessage}'!==''){
                this.$q.notify({
                    type: 'negative',
                    message: '<fmt:LValueParam checkParam="false" message="registrNotUnique" params="${param.registrationMessage}"/>',
                    position:'center',
                    icon: 'report_problem'
                })
            }if('${param.errorMessage}'!==''){
                this.$q.notify({
                    type: 'negative',
                    message: '${param.errorMessage}',
                    position:'center',
                    icon: 'report_problem'
                })
            }
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
