<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<script type="text/javascript">
    $(document).ready(function () {
        $('form input[name="dateOfBirth"]').on('change invalid', function () {
            var textfield = $(this).get(0);

            // 'setCustomValidity not only sets the message, but also marks
            // the field as invalid. In order to see whether the field really is
            // invalid, we have to remove the message first
            textfield.setCustomValidity('');
            if (textfield.validity.patternMismatch) {
                textfield.setCustomValidity('Wprowadź poprawny format daty dd.mm.rrrr');
            }
        });

        $('form input[name="height"]').on('change invalid', function () {
            var textfield = $(this).get(0);
            textfield.setCustomValidity('');
            if (textfield.validity.patternMismatch) {
                textfield.setCustomValidity('Wprowadź poprawny wzrost');
            }
        });

        $('form input[name="name"]').on('change invalid', function () {
            var textfield = $(this).get(0);
            textfield.setCustomValidity('');
            if (textfield.validity.patternMismatch) {
                textfield.setCustomValidity('Wprowadź poprawne imię');
            }
        });

        $('form input[name="surname"]').on('change invalid', function () {
            var textfield = $(this).get(0);
            textfield.setCustomValidity('');
            if (textfield.validity.patternMismatch) {
                textfield.setCustomValidity('Wprowadź poprawne nazwisko');
            }
        });
    });
</script>