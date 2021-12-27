<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:main>
    <jsp:body>
        <form action="" method="post" >
        <div class="card">
            <h4 class="card-header">
                Profile
            </h4>

            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Name</span>
                </div>
                <input type="text" class="form-control" value="${profile.name}" name="name" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">
            </div>

            <div class="input-group mb-3">
                <div class="input-group-append">
                    <span class="input-group-text" id="basic-addon2"><i class="fa fa-envelope" aria-hidden="true"></i></span>
                </div>
                <input type="text" class="form-control" placeholder="@examole.com" value="${profile.email}" name="email" aria-label="Recipient's username" aria-describedby="basic-addon2">
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon3"><i class="fa fa-map-marker" aria-hidden="true"></i></span>
                </div>
                <input type="text" class="form-control" value="${profile.address}" name="address" id="basic-url" aria-describedby="basic-addon3">
            </div>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">Note</span>
                </div>
                <textarea class="form-control" aria-label="With textarea"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">
                Save Profile
            </button>
        </div>

            <div class="card mt-4">
                <div class="card-header">
                    <h4 >
                        Điểm Đánh Giá: ${profile.mark}
                    </h4>
                    <h6>Top 10 đánh giá mới nhất</h6>
                </div>

                <c:choose>
                <c:when test="${empty bangdanhgia}">
                    <div class="card-body">
                        <p class="card-text">Không có đánh giá.</p>
                    </div>
                </c:when>
                <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Comment</th>
                        <th>Trạng Thái</th>
                    </tr>
                    </thead>
                    <c:forEach items="${bangdanhgia}" var="c">
                    <tbody>
                    <tr>
                        <td scope="row">${c.name}</td>
                        <td>${c.comment}</td>
                        <td> <c:if test="${c.trangthai ==1}">
                            <i class="fa fa-thumbs-up" aria-hidden="true"></i>
                        </c:if>
                            <c:if test="${c.trangthai ==0}">
                                <i class="fa fa-thumbs-o-down" aria-hidden="true"></i>
                        </c:if>
                        </td>
                    </tr>
                    </tbody>
                    </c:forEach>
                </table>
                </c:otherwise>
                </c:choose>
            </div>

            <div class="card mt-4">
                <h4 class="card-header">
                   Các Sản Phẩm Đang Đấu Giá
                </h4>
                <c:choose>
                    <c:when test="${empty dsachsanpham}">
                        <div class="card-body">
                            <p class="card-text">Không có sản phẩm đang tham gia đấu giá.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Tên Sản Phẩm</th>
                                <th>Giá Hiện Tại</th>
                                <th></th>
                            </tr>
                            </thead>
                            <c:forEach items="${dsachsanpham}" var="c">
                                <tbody>
                                <tr>
                                    <td scope="row">${c.name}</td>
                                    <td>${c.current_Price}</td>
                                    <td> <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/Product/Detail?id=${c.idProduct}" role="button">
                                        <i class="fa fa-eye" aria-hidden="true"></i>
                                    </a></td>
                                </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </form>
    </jsp:body>
</t:main>