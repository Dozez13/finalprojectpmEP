<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<q-toolbar  class="bg-primary text-white shadow-2 q-pa-md">
    <q-separator dark vertical inset></q-separator>
    <span class="q-ml-md text-h5"><fmt:localeValue key="headerCompanyName"/></span>
    <q-btn size="md" flat round dense icon="menu" class="q-mr-sm"></q-btn>
    <q-separator dark vertical inset></q-separator>
    <q-btn size="md" @click="goHome()" stretch flat label="<fmt:localeValue key="headerHomePage"/>"></q-btn>
    <q-space></q-space>
    <q-btn-dropdown size="md" icon="perm_identity" stretch flat label="<fmt:localeValue key="headerAccountSettings"/>" >
        <div class="row no-wrap q-pa-md justify-around">
            <c:if test="${sessionScope.Login==null}">
            <q-btn label="<fmt:localeValue key="singIn"/>" color="primary" size="md" @click="() => { showDialogD = true }"></q-btn>
            <div>
                <q-dialog v-model="showDialogD" no-backdrop-dismiss>
                    <div>
                        <q-form
                                action="${pageContext.request.contextPath}/pages/guest/login" method="post"
                        >
                            <q-card style="width: 400px;">
                                <q-toolbar class="bg-primary text-white">
                                    <q-toolbar-title>
                                        <fmt:localeValue key="singIn"/>
                                    </q-toolbar-title>
                                    <q-btn flat round color="white" icon="close" v-close-popup></q-btn>
                                </q-toolbar>
                                <q-card-section class="inset-shadow"/>
                                <q-input
                                        filled
                                        debounce="300"
                                        type="text"
                                        :hint="LogHintD"
                                        v-model="loginD"
                                        label="<fmt:localeValue key="dialogLogin"/>"
                                        name="login"
                                        class="q-ma-xs"
                                        :rules="[ val => val.match(/(^([a-z-а-яА-ЯЇЄЁЭїєёэ]{5,19})$)/ig)!=null || '<fmt:localeValue key="loginValidation" />']"
                                >
                                    <template v-slot:before>
                                        <q-icon name="login"></q-icon>
                                    </template>
                                </q-input>
                                <q-input v-model="passwordD"
                                         class="q-ma-xs"
                                         debounce="300"
                                         name="psw"
                                         label="<fmt:localeValue key="dialogPassword"/>"
                                         :rules="[ val => val.match(/(^([\wа-яА-ЯЇЄЁЭїєёэ]{5,20})$)/ig)!=null || '<fmt:localeValue key="passwordValidation"/>']"
                                         filled :type="isPwdD ? 'password' : 'text'"
                                         :hint="PasswordHintD">
                                    <template v-slot:before>
                                        <q-icon
                                                :name="isPwdD ? 'visibility_off' : 'visibility'"
                                                class="cursor-pointer"
                                                @click="isPwdD = !isPwdD"
                                        ></q-icon>
                                    </template>
                                </q-input>
                                <q-btn class="q-ma-md" label="<fmt:localeValue key="dialogLogInToAccount"/>" type="submit" color="primary"/>

                            </q-card>
                        </q-form>
                    </div>
                </q-dialog>
            </div>
            <q-btn
                    color="primary"
                    label="<fmt:localeValue key="headerSignUp"/>"
                    push
                    @click="RegistrationPage()"
                    col="col-6"
                    size="md"
                    v-close-popup
            ></q-btn>
            </c:if>
            <c:if test="${sessionScope.Login!=null}">
                <div class="column justify-center">
                    <div class="text-h6"><fmt:localeValue key="indexSettings"/></div>
                    <q-btn  color="primary"stretch flat label="<fmt:localeValue key="loggedProfile"/>"></q-btn>
                </div>

                <q-separator vertical inset class="q-mx-lg"></q-separator>
                <div class="column items-center">
                    <q-avatar size="72px" icon="portrait"></q-avatar>
                    <div class="text-subtitle1 q-mt-md q-mb-xs">${sessionScope.Login}</div>
                    <div class="text-subtitle1 q-mt-md q-mb-xs"> ${sessionScope.userType}</div>
                    <q-form
                            action="${pageContext.request.contextPath}/pages/user/logOut" method="post"
                    >
                    <q-btn
                            color="primary"
                            label="<fmt:localeValue key="loggedLogout"/>"
                            push
                            size="md"
                            v-close-popup
                            type="submit"
                    ></q-btn>
                    </q-form>
                </div>
            </c:if>
        </div>
    </q-btn-dropdown>
    <c:if test="${sessionScope.Login!=null}">
        <q-btn size="md" @click="MakeOrder()" stretch flat label="<fmt:localeValue key="headerMakeOrder"/>"></q-btn>
    </c:if>
    <q-separator dark vertical></q-separator>
    <q-btn size="md" :color="Eng" @click="ChangeToEng()"  label="EN"></q-btn>
    <q-btn size="md" :color="Ua" @click="ChangeToUa()"  label="UA"></q-btn>
    <q-btn size="md" :color="Ru" @click="ChangeToRu()"  label="RU"></q-btn>
    <c:if test="${'administrator'.equals(sessionScope.userType)}">
        <q-btn size="md" @click="CheckOrders()" stretch flat label="<fmt:localeValue key="ClientsOrders"/>"></q-btn>
    </c:if>

</q-toolbar>
