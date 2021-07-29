<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="/WEB-INF/ResourceBundleUTF.tld"%>
<%@taglib prefix="log" uri="http://logging.apache.org/log4j/tld/log"%>
<log:setLogger logger="orders" var="orders"/>
<log:log level="info" logger="${orders}" message="Orders jsp has been visited"/>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang :pageContext.request.locale}" scope="session" />
<fmt:setLocale name="${lang}" />
<fmt:setBundle basename="resources" />
<!DOCTYPE html>
<html lang="${lang}">
<head>
    <title>Orders</title>
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
    <div class="q-pa-md row justify-center">
        <div class="col-10">
            <q-card  class="q-ma-md col">
                <q-card-section class="text-center text-h5 q-pt-none">
                    <fmt:localeValue key="ordersTitleA"/>
                </q-card-section>
            </q-card>
            <q-table ordersTableNoDataLabel
                    title="<fmt:localeValue key="ordersTitleA"/>"
                    no-data-label=" <fmt:localeValue key="ordersTableNoDataLabel"/>"
                    no-results-label="<fmt:localeValue key="ordersTableAfterFilteringLabel"/>"
                    :data="data"
                    :columns="columns"
                    :pagination.sync="pagination"
                    :loading="loading"
                    :filter="filter"
                    @request="onRequest"
                    binary-state-sort
            >
                <template v-slot:top-row>
                    <q-tr>
                        <q-td>
                        </q-td>
                        <q-td>
                            <q-input type="number" debounce="300" outlined v-model="filter.orderId" label="<fmt:localeValue key="ordersLabelOrderId"/>" dense></q-input>
                        </q-td>
                        <q-td>
                            <q-input type="number" debounce="300" outlined v-model="filter.userId" label="<fmt:localeValue key="ordersLabelUserId"/>" dense></q-input>
                        </q-td>
                        <q-td>
                            <q-input type="number" debounce="300" outlined v-model="filter.carId" label="<fmt:localeValue key="ordersLabelCarId"/>" dense></q-input>
                        </q-td>
                        <q-td>
                            <q-input type="text" debounce="300" outlined v-model="filter.userAddress" label="<fmt:localeValue key="ordersLabelUserAddress"/>" dense></q-input>
                        </q-td>
                        <q-td>
                            <q-input type="text" debounce="300" outlined v-model="filter.userDestination" label="<fmt:localeValue key="ordersLabelUserDestination"/>" dense></q-input>
                        </q-td>
                        <q-td>
                            <q-input type="number" mask="###.#" debounce="300" outlined v-model="filter.orderCost" label="<fmt:localeValue key="ordersLabelOrderCost"/>" dense></q-input>
                        </q-td>
                        <q-td>
                            <q-input debounce="400" filled v-model="filter.orderDate" mask="####-##-## ##:##">
                                <template v-slot:prepend>
                                    <q-icon name="event" class="cursor-pointer">
                                        <q-popup-proxy transition-show="scale" transition-hide="scale">
                                            <q-date v-model="filter.orderDate" mask="YYYY-MM-DD HH:mm">
                                                <div class="row items-center justify-end">
                                                    <q-btn v-close-popup label="Close" color="primary" flat></q-btn>
                                                </div>
                                            </q-date>
                                        </q-popup-proxy>
                                    </q-icon>
                                </template>

                                <template v-slot:append>
                                    <q-icon name="access_time" class="cursor-pointer">
                                        <q-popup-proxy transition-show="scale" transition-hide="scale">
                                            <q-time v-model="filter.orderDate" mask="YYYY-MM-DD HH:mm" format24h>
                                                <div class="row items-center justify-end">
                                                    <q-btn v-close-popup label="Close" color="primary" flat></q-btn>
                                                </div>
                                            </q-time>
                                        </q-popup-proxy>
                                    </q-icon>
                                </template>
                            </q-input>

                        </q-td>

                    </q-tr>
                </template>

            </q-table>

        </div>
    </div>
</div>
</body>
<script>
    const dataTable = parseInt('${Count}',10);
    new Vue({
        el: '#q-app',
        data () {
            return {
                Eng:'',
                Ua:'',
                Ru:'',
                filter: {
                    orderId: '',
                    userId:'',
                    carId: '',
                    userAddress: '',
                    userDestination: '',
                    orderCost:  '',
                    orderDate: ''
                },
                loading: false,
                pagination: {
                    sortBy: 'orderId',
                    descending: false,
                    page: 1,
                    rowsPerPage: 3,
                    rowsNumber: dataTable
                },
                columns: [
                    {
                        name: 'orderId',
                        required: true,
                        label: '<fmt:localeValue key="ordersLabelOrderId"/>',
                        align: 'left',
                        field: 'orderId',
                        sortable: true
                    },
                    { name: 'userId', align: 'center', label: '<fmt:localeValue key="ordersLabelUserId"/>', field: 'userId', sortable: true },
                    { name: 'carId', label: '<fmt:localeValue key="ordersLabelCarId"/>', field: 'carId', sortable: true },
                    { name: 'userAddress', label: '<fmt:localeValue key="ordersLabelUserAddress"/>', field: 'userAddress', sortable: true },
                    { name: 'userDestination', label: '<fmt:localeValue key="ordersLabelUserDestination"/>', field: 'userDestination', sortable: true },
                    { name: 'orderCost', label: '<fmt:localeValue key="ordersLabelOrderCost"/>', field: 'orderCost', sortable: true },
                    { name: 'orderDate', label: '<fmt:localeValue key="ordersLabelOrderDate"/>', field: 'orderDate', sortable: true}
                ],

                data: [],

            }
        },
        mounted: function(){
            window.history.pushState({}, document.title,window.location.href.split(/[?#]/)[0]);
            this.onRequest({
                pagination: this.pagination,
                filter: this.filter
            })
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
        },

        methods: {
             async onRequest(props) {

                 const {page, rowsPerPage, sortBy, descending} = props.pagination
                 const filter = Object.fromEntries(Object.entries(props.filter).filter(([_, v]) => !!v));

                 this.loading = true


                 // update rowsCount with appropriate value

                 // get all rows if "All" (0) is selected


                 // calculate starting row of data
                 const startRow = (page - 1) * rowsPerPage

                 // fetch data from "server"
                 const returnedData = await this.fetchFromServer(startRow, filter, sortBy, descending, rowsPerPage)
                 if(Object.keys(filter).length!==0){
                     this.pagination.rowsNumber = returnedData.length
                 }
                 
                 // clear out existing data and add new
                 this.data.splice(0, this.data.length, ...returnedData)

                 // don't forget to update local pagination object
                 this.pagination.page = page
                 this.pagination.rowsPerPage = rowsPerPage
                 this.pagination.sortBy = sortBy
                 this.pagination.descending = descending

                 // ...and turn of loading indicator
                 this.loading = false

             },


            // SELECT * FROM ... WHERE...LIMIT...
             async fetchFromServer (startRow,filter, sortBy, descending,rowsPerPage) {
                const response = await axios.get("${pageContext.request.contextPath}"+"/pages/admin/ordersJson",{
                    params: {
                        startRow: startRow,
                        filter:filter,
                        sortBy:sortBy,
                        descending:descending,
                        rowsPerPage:rowsPerPage
                    }
                })
               return response.data
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
            }




        }
    })
</script>
</html>

