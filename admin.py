from flask import *
from database import *


admin=Blueprint("admin",__name__)


@admin.route('/admin_home',methods=['get','post'])
def admin_home():
    return render_template("admin_home.html")


@admin.route('/admin_verify_hospital')
def admin_verify_hospital():

    data={}
    qry="select * from hospital inner join login using(login_id)"
    res=select(qry)
    data['hospital']=res

    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    else:
        action=None

    if action=='accept':
        qrty="update login set user_type='hospital' where login_id='%s'"%(id)
        update(qrty)
        return ("<script>alert('Accept successfull');window.location='/admin_verify_hospital'</script>")


    if action=='reject':
        qrty1="update login set user_type='pending' where login_id='%s'"%(id)
        update(qrty1)

        return ("<script>alert('Reject successfull');window.location='/admin_verify_hospital'</script>")


    return render_template("admin_verify_hospital.html",data=data)


@admin.route('/admin_view_doctors')
def admin_view_doctors():
    data={}

    qry="select * from doctors"
    res=select(qry)
    data['view']=res

    return render_template("admin_view_doctors.html",data=data)

@admin.route('/admin_view_users')
def admin_view_users():
    data={}
    qry="select * from user"
    res=select(qry)
    data['view']=res

    return render_template("admin_view_users.html",data=data)

@admin.route('/admin_view_complaints')
def admin_view_complaints():

    data={}

    qry="select * from complaint"
    res=select(qry)
    data['complaints']=res


    return render_template("admin_view_complaints.html",data=data)


@admin.route('/admin_send_reply',methods=['get','post'])
def admin_send_reply():
    id=request.args['id']

    if 'send' in request.form:

        Reply=request.form['rep']

        qry="update complaint set reply='%s' where complaint_id='%s'"%(Reply,id)
        update(qry)
        return ("<script>alert('Send successfull');window.location='/admin_view_complaints'</script>")

    return render_template("admin_send_reply.html")