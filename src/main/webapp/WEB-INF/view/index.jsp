<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="/WEB-INF/ResourceBundleUTF.tld"%>
<%@taglib prefix="log" uri="http://logging.apache.org/log4j/tld/log"%>
<%@taglib prefix="image"   tagdir="/WEB-INF/tags/imageHelper"%>

<log:setLogger logger="indexJsp" var="indexJsp"/>
<log:log level="info" logger="${indexJsp}" message="Index jsp has been visited"/>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang :pageContext.request.locale.language}" scope="session" />
<fmt:setLocale name="${lang}" />
<fmt:setBundle basename="resources" />
<!DOCTYPE html>
<html lang="${lang}">
<head>
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
        <div class="row justify-center">
        <div style="border-bottom: 1px solid #003cd4" class="q-mt-xl q-mb-md col-6 text-center text-h4 text-weight-medium">
            <fmt:localeValue key="indexTitle" />
        </div>
        </div>
        <div class="q-mt-md row justify-center">
            <q-carousel
                    swipeable
                    animated
                    class="col-6 bg-grey-1 shadow-2 rounded-borders"
                    v-model="slide"
                    infinite
                    height="500px"
                    transition-prev="scale"
                    transition-next="scale"
                    ref="carousel"
                    padding
            >

                <c:forEach items="${Categories}" var="categories" varStatus="loop">
                    <q-carousel-slide :name="${loop.index+1}">
                        <c:forEach items="${Cars}" var="car" >

                            <c:if test="${car.carCategory.equals(categories.carCategoryName)}">
                                <q-img
                                        src="data:image/jpeg;base64,<image:convertToBase64 image="${car.carImage}"/>"
                                        style="width:49%;height:51%;"

                                >

                                    <div class="absolute-bottom text-subtitle1 text-center">
                                        <fmt:localeValue key="${car.carCategory}" />
                                    </div>
                                </q-img>
                            </c:if>

                        </c:forEach>
                    </q-carousel-slide>

                </c:forEach>
                <template v-slot:control>
                    <q-carousel-control
                            position="bottom-right"
                            :offset="[18, 18]"
                            class="q-gutter-xs"
                    >
                        <q-btn
                                push round dense color="primary" text-color="white" icon="arrow_left"
                                @click="$refs.carousel.previous()"
                        ></q-btn>
                        <q-btn
                                push round dense color="primary" text-color="white" icon="arrow_right"
                                @click="$refs.carousel.next()"
                        ></q-btn>
                    </q-carousel-control>
                </template>
            </q-carousel>
        </div>
        <div class="q-mt-md row justify-center">
            <q-carousel
                    swipeable
                    animated
                    transition-prev="slide-left"
                    transition-next="slide-right"
                    class="col-6"
                    v-model="slide"
                    infinite
                    height="300px"

            >

                <c:forEach items="${Categories}" var="categories" varStatus="loop">
                    <q-carousel-slide :name="${loop.index+1}" class="row justify-center" >
                        <q-card class="q-ma-xs col-5 bg-primary" dark bordered>
                            <q-card-section>
                                <div class="text-h5">  <fmt:localeValue key="carCategory" /></div>
                            </q-card-section>
                            <q-card-section class="text-center text-h4 q-pt-none">
                                <fmt:localeValue key="${categories.carCategoryName}" />
                            </q-card-section>
                        </q-card>
                        <q-card class="q-ma-xs col-5 bg-primary" dark bordered>
                            <q-card-section>
                                <div class="text-h5"><fmt:localeValue key="costPerK" /></div>
                            </q-card-section>
                            <q-card-section class="text-center text-h4 q-pt-none">
                                    ${categories.costPerOneKilometer}
                            </q-card-section>
                        </q-card>
                        <q-card class="q-ma-xs col-5 bg-primary" dark bordered>
                            <q-card-section >
                                <div class="text-h5"><fmt:localeValue key="discount" /></div>
                            </q-card-section>
                            <q-card-section  class="text-center text-h4 q-pt-none">
                                    ${categories.discount*100}%
                            </q-card-section>
                        </q-card>
                    </q-carousel-slide>
                </c:forEach>
            </q-carousel>
        </div>
    </div>

</div>

</body>
<script>
    new Vue({
        el: '#q-app',
        data () {
            return {
                slide: 1,
                passwordD: '',
                loginD:'',
                isPwdD: true,
                LogHintD:'<fmt:localeValue key="LogHintD" />',
                PasswordHintD:'<fmt:localeValue key="PasswordHintD" />',
                showDialogD: false,
                Eng:'',
                Ua:'',
                Ru:''


            }
        },methods:{
            goHome(){
                window.location.replace('${pageContext.request.contextPath}'+'/pages/index?lang=${sessionScope.lang}');
            },
            RegistrationPage(){
                window.location.href ='${pageContext.request.contextPath}'+'/pages/guest/registration?lang=${sessionScope.lang}';

            },
            MakeOrder(){
                window.location.href='${pageContext.request.contextPath}'+'/pages/user/order?lang=${sessionScope.lang}';
            },
            CheckOrders(){
                window.location.href='${pageContext.request.contextPath}'+'/pages/admin/orders?lang=${sessionScope.lang}';
            },
            ChangeToEng(){
                window.location.href = window.location.href+'?lang=en'
            },
            ChangeToUa(){
                window.location.href = window.location.href+'?lang=ua'
            },
            ChangeToRu(){
                window.location.href = window.location.href+'?lang=ru'
            }
        },
        mounted: function(){
            window.history.pushState({}, document.title,window.location.href.split(/[?#]/)[0]);
            if('${param.errorMessage}'!==''){
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