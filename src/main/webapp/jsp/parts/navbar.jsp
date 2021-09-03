<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 30.08.2021
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="b-example-divider"></div>
<header class="p-3 bg-dark text-white">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">

            <ul class="nav col col-lg-auto me-lg-auto mr-auto mb-2 justify-content-center mb-md-0">
                <li><a href="/jsp/pages/home.jsp" class="nav-link px-2 text-secondary">Home</a></li>
                <li><a href="/jsp/pages/ranking.jsp" class="nav-link px-2 text-white">Ranking</a></li>
                <li><a href="https://getbootstrap.com/docs/5.1/examples/headers/#" class="nav-link px-2 text-white">Play</a>
                </li>
                <li><a href="https://getbootstrap.com/docs/5.1/examples/headers/#"
                       class="nav-link px-2 text-white">FAQs</a></li>
                <li><a href="https://getbootstrap.com/docs/5.1/examples/headers/#" class="nav-link px-2 text-white">About</a>
                </li>
            </ul>

            <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                <input type="search" class="form-control form-control-dark" placeholder="Search..."
                       aria-label="Search">
            </form>

            <div class="text-end">
                <a type="button" href="/jsp/pages/login.jsp" class="btn btn-outline-light me-2">Login</a>
                <a type="button" href="/jsp/pages/sign-up.jsp" class="btn btn-warning">Sign-up</a>
            </div>
        </div>
    </div>
</header>
