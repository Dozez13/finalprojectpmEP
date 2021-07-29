<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/ResourceBundleUTF.tld"%>
<%@taglib prefix="log" uri="http://logging.apache.org/log4j/tld/log"%>
<log:setLogger logger="order" var="order"/>
<log:log level="info" logger="${order}" message="Order jsp has been visited"/>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang :pageContext.request.locale}" scope="session" />
<fmt:setLocale name="${lang}" />
<fmt:setBundle basename="resources" />
<!DOCTYPE html>
<html lang="${lang}">
<head>
    <title>Order</title>
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
    <div class="q-pa-md row justify-center">
        <div class="col-4">
            <q-card  class="q-ma-md col">
                <q-card-section class="text-center text-h5 q-pt-none">
                    <fmt:localeValue key="orderTaxi"/>
                </q-card-section>
            </q-card>
            <q-dialog
                    v-model="Message"
            >
                <q-card style="width: 700px; max-width: 80vw;">
                    <q-card-section>
                        <div class="text-h6"><fmt:localeValue key="takenTime"/></div>
                    </q-card-section>

                    <q-card-section class="q-pt-none">
                        <c:if test="${takenTime!=null}">
                            <fmt:LValueParam checkParam="true" message="orderTakenTime" params="${takenTime}"/>
                        </c:if>
                    </q-card-section>
                    <q-card-actions align="right" class="bg-white text-teal">
                        <q-btn flat label="OK" v-close-popup></q-btn>
                    </q-card-actions>
                </q-card>
            </q-dialog>
            <div :style="style">
                <q-card :class="{col:hasError, 'text-h6': hasError, 'bg-amber-10': hasError }">
                    <q-card-section class="text-center">
                        <c:if test="${orderData!=null}">
                            <c:if test="${orderData.equals('Account doesnt have money for order')}">
                                <fmt:localeValue key="AccountMoneyFail"/>
                            </c:if>
                            <c:if test="${!orderData.equals('Account doesnt have money for order')}">
                                <fmt:LValueParam checkParam="true" message="orderFailMessage" params="${orderData}"/>
                            </c:if>
                        </c:if>
                    </q-card-section>
                </q-card>
            </div>
            <q-form action="${pageContext.request.contextPath}/pages/user/doOrder" method="post">
                <q-input
                        filled
                        outlined
                        debounce="300"
                        type="text"
                        :hint="YHint"
                        v-model="YAddress"
                        label="<fmt:localeValue key="orderFirstInput"/>"
                        name="userAddress"
                        class="q-ma-md"
                        :rules="[ val => val.match(/(^(([\wа-яА-ЯЇІЄЁЭїієёэ\d ]{1,20}[,][ ]){3,4}[\wа-яА-ЯЇІЄЁЭїієёэ\d]{1,20})$)/ig)!=null || '<fmt:localeValue key="orderValidHint"/>']"
                >

                </q-input>
                <q-input
                        outlined
                        filled
                        debounce="300"
                        type="text"
                        :hint="DHint"
                        v-model="DAddress"
                        label="<fmt:localeValue key="orderSecondInput"/>"
                        name="userDestination"
                        class="q-ma-md"
                        :rules="[ val => val.match(/(^(([\wа-яА-ЯЇІЄЁЭїієёэ\d ]{1,20}[,][ ]){3,4}[\wа-яА-ЯЇЄІЁЭїієёэ\d]{1,20})$)/ig)!=null || '<fmt:localeValue key="orderValidHint"/>']"
                >

                </q-input>
                <div v-for="(item, index) in items">
                <q-input
                        outlined
                        filled
                        type="number"
                        v-model="item.numOfPas"
                        label="<fmt:localeValue key="orderThirdInput"/>"
                        name ="numOfPas"
                        debounce="300"
                        :rules="[val => val !== null && val !== '' && val !== 0 || 'Please type number of passenger']"
                ></q-input>

                <div class="row justify-center">
                      <div>
                          <q-btn-toggle
                                  v-model="item.categories"
                                  push
                                  toggle-color="lime-6"
                                  name="categories"
                                  color="amber-7"
                                  size="md"
                                  :options='${requestScope.carCategoriesButtons}'
                          >
                              <c:forEach items="${carCategories}" var="carCategory" varStatus="loop">
                                  <template v-slot:${loop.index+1}>
                                      <div class="text-center">
                                          <fmt:localeValue key="${carCategory.carCategoryName}"/>
                                      </div>
                                  </template>
                              </c:forEach>
                          </q-btn-toggle>
                     </div>

            </div>
                    <q-btn color="primary" class="q-mt-md" @click="deleteItemForm(index)"><fmt:localeValue key="orderRemoveCar"/></q-btn>
                </div>
                <q-btn color="primary" style="width:100%;" class="q-mt-md" @click="addNewItemForm"><fmt:localeValue key="orderAddCar"/></q-btn><br>
                <q-btn style="width:100%;" class="q-mt-md" label="<fmt:localeValue key="orderTaxi"/>" type="submit" color="primary"></q-btn>
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
                YAddress:'',
                DAddress:'',
                sel:null,
                Eng:'',
                Ua:'',
                Ru:'',
                hasError:'${NotAvailable}'!=='',
                style: { width: '0px', height: '0px' },
                DHint:'<fmt:localeValue key="orderDAddressHint"/>',
                YHint:'<fmt:localeValue key="orderAddressHint"/>',
                Message:'${takenTime}' !== '',
                items:[
                    {
                        numOfPas:0,
                        categories:'${carCategories[0].carCategoryName}'
                    }
                ],

            }
        },
        methods:{
            goHome(){
                window.location.href ='${pageContext.request.contextPath}'+'/pages/index?lang=${sessionScope.lang}';
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
            profilePage(){
                window.location.href ='${pageContext.request.contextPath}'+'/pages/user/profile?lang=${sessionScope.lang}';
            },
            changeToEng(){
                window.location.href = window.location.href+'?lang=en'
            },
            changeToUa(){
                window.location.href = window.location.href+'?lang=ua'
            },
            changeToRu(){
                window.location.href = window.location.href+'?lang=ru'
            },
            addNewItemForm(){
                this.items.push({
                    numOfPas:0,
                    categories:'${carCategories[0].carCategoryName}'
                })},
            deleteItemForm(index){
                    this.items.splice(index,1)


            }
        },
        mounted: function(){
            window.history.pushState({}, document.title,window.location.href.split(/[?#]/)[0]);
            if('${NotAvailable}'!=='') {
                alert
                this.style = {
                    width: 'auto',
                    height:'auto'
                }
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
