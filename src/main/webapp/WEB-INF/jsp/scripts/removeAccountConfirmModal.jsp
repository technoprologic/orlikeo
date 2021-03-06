<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/account/remove" var="removeAccountUrl" />

<!-- RemoveAccountModal -->
<div class="modal fade" id="removeAccountModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
      </div>
      <div class="modal-body">
        <form:form action="${removeAccountUrl}" method="POST">
          <input type="hidden" class="form-control" name="email" id="email" />
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
            <button type="submit" class="btn btn-success">Usuń konto</button>
          </div>
        </form:form>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
  $('#removeAccountModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    /*var recipient = button.data('href') */// Extract info from data-* attributes
    // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
    // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
    var modal = $(this)
    modal.find('.modal-title').text('Usunąć konto ?')
  });
</script>