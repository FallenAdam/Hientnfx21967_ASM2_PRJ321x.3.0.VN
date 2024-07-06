
ClassicEditor.create(document.querySelector('#editor')).then(eidt => {
    console.log("da" + eidt);
})
    .catch(error => {
        console.error(error);
    });
ClassicEditor.create(document.querySelector('#editorN')).then(eidt => {
    console.log("da" + eidt);
})
    .catch(error => {
        console.error(error);
    });

// Upload ảnh của User
$('#fileUpload').change(function (e) {
    if (window.FormData !== undefined) {
        var fileUpload = $('#fileUpload').get(0);
        var files = fileUpload.files;
        var id = $("#id").val();
        var formData = new FormData();
        formData.append('file', files[0]);
        formData.append('id', id);
        if (files[0] == null) {
            // document.getElementById("change").style.backgroundColor = 'red';
            // $('#text').val(" ❌ Cập nhật ảnh thất bại");
            $(".toast").toast("show");
        } else {
            $.ajax(
                {
                    type: 'POST',
                    url: '/user/upload/',
                    contentType: false,
                    processData: false,
                    data: formData,
                    success: function (urlImage) {
                        console.log(urlImage)
                        if (urlImage == "Error") {
                            document.getElementById("change").style.backgroundColor = 'red';
                            swal({
                                title: 'Cập nhật ảnh đại diện thất bại!',
                                /* text: 'Redirecting...', */
                                icon: 'error',
                                timer: 3000,
                                buttons: true,
                                type: 'error'
                            })
                            $("#divImage").css("display", "block")
                        } else {
                            $('#avatar').attr('src', '/resources/images?name=' + urlImage)
                            swal({
                                title: 'Cập nhật ảnh đại diện thành công!',
                                /* text: 'Redirecting...', */
                                icon: 'success',
                                timer: 3000,
                                buttons: true,
                                type: 'success'
                            })
                        }

                    },
                    error: function (err) {
                        alert(err);
                    }
                }
            )
        }

    }
})


// Upload ảnh của Company
$('#fileUpload2').change(function () {
    if (window.FormData !== undefined) {
        var fileUpload = $('#fileUpload2').get(0);
        var files = fileUpload.files;
        var id = $("#id").val();
        var formData = new FormData();
        formData.append('file', files[0]);
        formData.append('id', id);
        if(files[0] == null){
            // document.getElementById("change").style.backgroundColor = 'red';
            // $('#text').val(" ❌ Cập nhật ảnh thất bại");
            $(".toast").toast("show");
        } else {
            $.ajax(
                {
                    type: 'POST',
                    url: '/user/upload-company/',
                    contentType: false,
                    processData: false,
                    data: formData,
                    success: function (urlImage) {
                        console.log(urlImage)
                        if(urlImage == "Error"){
                            document.getElementById("change").style.backgroundColor = 'red';
                            swal({
                                title: 'Cập nhật logo thất bại!',
                                /* text: 'Redirecting...', */
                                icon: 'error',
                                timer: 3000,
                                buttons: true,
                                type: 'error'
                            })
                            $("#divLogo").css("display","block")
                        }else{
                            $('#avatar1').attr('src', '/resources/images/company-logo?name=' + urlImage)
                            swal({
                                title: 'Cập nhật logo thành công!',
                                /* text: 'Redirecting...', */
                                icon: 'success',
                                timer: 3000,
                                buttons: true,
                                type: 'success'
                            })
                        }

                    },
                    error: function (err) {
                        alert(err);
                    }
                }
            )
        }

    }
})

// Upload CV
$('#fileUpload1').change(function () {
    if (window.FormData !== undefined) {
        var fileUpload = $('#fileUpload1').get(0);
        var files = fileUpload.files;
        var formData = new FormData();
        formData.append('file', files[0]);
        if(files[0] == null){
            // document.getElementById("change").style.backgroundColor = 'red';
            // $('#text').val(" ❌ Cập nhật ảnh thất bại");
            $(".toast").toast("show");
        } else {
            $.ajax(
                {
                    type: 'POST',
                    url: '/user/uploadCv',
                    contentType: false,
                    processData: false,
                    data: formData,
                    success: function (urlFile) {
                        console.log(urlFile)
                        if(urlFile == "false"){
                            // document.getElementById("change").style.backgroundColor = 'red';

                            swal({
                                title: 'Cần chọn đúng loại file (PDF)!',
                                /* text: 'Redirecting...', */
                                icon: 'error',
                                timer: 3000,
                                buttons: true,
                                type: 'error'
                            })
                             // $("#divImage").css("display","block")
                        }else{
                            // $('#avatar2').attr('src','/resources/upload_files?name=' + urlFile)
                            document.getElementById('nameCv').innerHTML = 'Xem cv';
                            document.getElementById('nameCv').href = "http://localhost:8080/resources/upload_files/" + urlFile ; //or grab it by tagname etc
                            document.getElementById('xoa').innerHTML = 'Xóa cv';
                            document.getElementById("cvName").innerHTML = urlFile;
                            document.getElementById("cvXoa").innerHTML = urlFile;

                            swal({
                                title: 'Cập nhật CV thành công!',
                                /* text: 'Redirecting...', */
                                icon: 'success',
                                timer: 3000,
                                buttons: true,
                                type: 'success'
                            })
                        }

                    },
                    error: function (err) {
                        alert(err);
                    }
                }
            )
        }

    }
})


