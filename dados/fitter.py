import csv 

with open('enderecos.csv') as file:
    reader  =  csv.DictReader(file)
    print('Cep,Rua,Bairro,Lat,Lng')
    for row in reader:
        if((row['nome_localidade'] == "Campina Grande") and (row['sigla_uf'] == "PB")):
            print(row['cep'] + ',' + row["logr_compl"]  + "," + row["bairro"] + "," + row['latitude']
            + ","+ row["longitude"])
