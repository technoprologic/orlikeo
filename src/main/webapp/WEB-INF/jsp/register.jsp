<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<c:url value="/help" var="helpUrl" />
<c:url value="/register" var="createAccountUrl" />

<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
        <h1 class="text-center login-title">Zaloguj i korzystaj z możliwości jakie daje Orlikeo!</h1>
        <div class="account-wall">
            <form method="post">
				<div class="form-group">
        			<label for="validate-text">Validate Text</label>
					<div class="input-group">
						<input type="text" class="form-control" name="validate-text" id="validate-text" placeholder="Validate Text" required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
				<div class="form-group">
        			<label for="validate-optional">Optional</label>
					<div class="input-group">
						<input type="text" class="form-control" name="validate-optional" id="validate-optional" placeholder="Optional">
						<span class="input-group-addon info"><span class="glyphicon glyphicon-asterisk"></span></span>
					</div>
				</div>
    			<div class="form-group">
        			<label for="validate-optional">Already Validated!</label>
    				<div class="input-group">
						<input type="text" class="form-control" name="validate-text" id="validate-text" placeholder="Validate Text" value="Validated!" required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
				<div class="form-group">
        			<label for="validate-email">Validate Email</label>
					<div class="input-group" data-validate="email">
						<input type="text" class="form-control" name="validate-email" id="validate-email" placeholder="Validate Email" required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
    			<div class="form-group">
        			<label for="validate-phone">Validate Phone</label>
					<div class="input-group" data-validate="phone">
						<input type="text" class="form-control" name="validate-phone" id="validate-phone" placeholder="476 375 374" required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
        		<div class="form-group">
        			<label for="validate-length">Minimum Length</label>
					<div class="input-group" data-validate="length" data-length="5">
						<textarea type="text" class="form-control" name="validate-length" id="validate-length" placeholder="Validate Length" required></textarea>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
                <div class="form-group">
            		<label for="validate-select">Validate Select</label>
					<div class="input-group">
                        <select class="form-control" name="validate-select" id="validate-select" placeholder="Validate Select" required>
                            <option value="">Select an item</option>
                            <option value="item_1">Item 1</option>
                            <option value="item_2">Item 2</option>
                            <option value="item_3">Item 3</option>
                        </select>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
            	<div class="form-group">
        			<label for="validate-number">Validate Number</label>
					<div class="input-group" data-validate="number">
						<input type="text" class="form-control" name="validate-number" id="validate-number" placeholder="Validate Number" required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
                <button type="submit" class="btn btn-lg btn-primary btn-block" disabled>Zarejestruj</button>
            </form>
            </div>
        </div>
    </div>
</div>