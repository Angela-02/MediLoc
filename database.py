import mysql.connector
localhost="bfibti0yjnkaeohfdcbs-mysql.services.clever-cloud.com"
user="u9zntq8wd1ny3yim"
password="q2ODa1InKwd3duBSYkvo"
database="bfibti0yjnkaeohfdcbs"


def select(q):
	con=mysql.connector.connect(user=user,password=password,host=localhost,database=database,port=3306)
	cur=con.cursor(dictionary=True)
	cur.execute(q)
	result=cur.fetchall()
	cur.close()
	con.close()
	return result

def insert(q):
	con=mysql.connector.connect(user=user,password=password,host=localhost,database=database,port=3306)
	cur=con.cursor(dictionary=True)
	cur.execute(q)
	con.commit()
	result=cur.lastrowid
	cur.close()
	con.close()
	return result

def update(q):
	con=mysql.connector.connect(user=user,password=password,host=localhost,database=database,port=3306)
	cur=con.cursor(dictionary=True)
	cur.execute(q)
	con.commit()
	res=cur.rowcount
	cur.close()
	con.close()
	return res

def delete(q):
	con=mysql.connector.connect(user=user,password=password,host=localhost,database=database,port=3306)
	cur=con.cursor(dictionary=True)
	cur.execute(q)
	con.commit()
	result=cur.rowcount
	cur.close()
	con.close()
	return result