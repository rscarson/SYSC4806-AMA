function inPlaceLink(url) {
    $.get(url);
    location.reload(true);
}

function title_not_empty() {
    if ($("#title").val().trim().length == 0) {
        $("#titleError").css("display", "");
        return false;
    }

    return true;
}

function vote(url, prefix, self) {
    var id = self.dataset.id;
    url = url + '?id=' + id;

    var up = $('#'+prefix+'up'+id);
    var dn = $('#'+prefix+'dn'+id);
    var count = $('#'+prefix+'count'+id);
    $.getJSON(url, function(data) {
        if (data['up']) {
            up.addClass('active');
            up.addClass('btn-success');
            up.removeClass('btn-default');
        } else {
            up.removeClass('active');
            up.removeClass('btn-success');
            up.addClass('btn-default');
        }

        if (data['dn']) {
            dn.addClass('active');
            dn.addClass('btn-danger');
        } else {
             dn.removeClass('active');
             dn.removeClass('btn-danger');
             dn.addClass('btn-default');
         }

        var countClass = (data['votes'] > 0) ? "text-success" : (data['votes'] < 0) ? "text-danger" : "";
        var $counter = $("<h4>", {"class": countClass});
        $counter.html(data['votes']);
        count.html($counter);
    });
}

$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null){
        return null;
    }
    else{
        return results[1] || 0;
    }
}

/**
 * Drop downs has to be rebound on every page load
 */
function bindSortDropDowns() {
    $("#sortby").on('change', function() {
        //alert("Value = " + $("#sortby").val());
        //alert("ID = " + $.urlParam("id"));
        var oldVal = $("#sortby").val();
        $.ajax({
            url: "/ama/view?id=" + $.urlParam("id") + "&sortby=" + $("#sortby").val(),
            content: document.body,
            success: function (result) {
                document.body.innerHTML = result;
                bindSortDropDowns();
                bindJumpButtons();
                //Set the drop down to the current sort val
                $("#sortby").val(oldVal);
            }
        });
    });

    $("#usersortby").on('change', function() {
        //alert("Value = " + $("#sortby").val());
        //alert("ID = " + $.urlParam("id"));
        var oldVal = $("#usersortby").val();
        $.ajax({
            url: "/user/view?id=" + $.urlParam("id") + "&sortby=" + $("#usersortby").val(),
            content: document.body,
            success: function (result) {
                document.body.innerHTML = result;
                bindSortDropDowns();
                bindJumpButtons();
                //Set the drop down to the current sort val
                $("#usersortby").val(oldVal);
            }
        });
    });
}

function bindJumpButtons() {
    $("#JumpComment").on('click', function () {
        window.location.href="#JumpAMA";
    });

    $("#JumpAMA").on('click', function() {
        window.location.href="#JumpComment";
    });
}

$(document).ready(function () {
    bindSortDropDowns();
    bindJumpButtons();
});