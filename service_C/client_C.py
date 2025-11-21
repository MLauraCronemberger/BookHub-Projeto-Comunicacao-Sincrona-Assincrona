# servico_c.py - Servidor REST (FastAPI) e Cliente gRPC Duplo

from fastapi import FastAPI, HTTPException
import grpc

# Stubs do Serviço A (Livro)
from stubs_service_A import catalogo_pb2 
from stubs_service_A import catalogo_pb2_grpc

# Stubs do Serviço B (Autor)
from stubs_service_B import catalogo_autores_pb2
from stubs_service_B import catalogo_autores_pb2_grpc

# --- Configurações ---
PORTA_C = 8000 
ENDERECO_A = 'localhost:5000' # Porta do Servidor A
ENDERECO_B = 'localhost:5002' # Porta do Servidor B (Node.js)

app = FastAPI(title="Serviço C - Orquestrador Síncrono")

# -----------------------------------------------
# 1. Clientes gRPC
# -----------------------------------------------

def chamar_servico_A(livro_id: str):
    """ Chamada gRPC ao Servidor A (Metadados do Livro) """
    try:
        with grpc.insecure_channel(ENDERECO_A) as channel:
            stub = catalogo_pb2_grpc.InfosLivroServiceStub(channel)
            request = catalogo_pb2.LivroRequest(livro_id=livro_id)
            response = stub.GetInfosLivro(request)
            
            return {
                "titulo": response.titulo,
                "genero": response.genero,
                "sinopse": response.sinopse,
                "ano_lancamento": response.ano_lancamento,
                "idioma_original": response.idioma_original
            }
            
    except grpc.RpcError as e:
        details = e.details() if e.details() else "Erro desconhecido na comunicação gRPC com A."
        print(f" Erro A: {details}")
        # Tratamento de erro específico
        if e.code() == grpc.StatusCode.NOT_FOUND:
             return {"erro": details, "status_code": 404}
        return {"erro": "Erro interno no Serviço A", "status_code": 500}


def chamar_servico_B(livro_id: str):
    """ Chamada gRPC ao Servidor B (Metadados do Autor) """
    try:
        with grpc.insecure_channel(ENDERECO_B) as channel:
            # Note o uso do novo stub: catalogo_autores_pb2_grpc
            stub = catalogo_autores_pb2_grpc.AutorServiceStub(channel) 
            # Note o uso do novo request: catalogo_autores_pb2.AutorRequest
            request = catalogo_autores_pb2.AutorRequest(livro_id=livro_id) 
            response = stub.GetInfosAutor(request)
            
            return {
                "nome_autor": response.nome_autor,
                "idade_autor": response.idade_autor,
                "nacionalidade": response.nacionalidade,
                "obras_conhecidas": list(response.obras_conhecidas)
            }
            
    except grpc.RpcError as e:
        details = e.details() if e.details() else "Erro desconhecido na comunicação gRPC com B."
        print(f"Erro B: {details}")
        if e.code() == grpc.StatusCode.NOT_FOUND:
             return {"erro": details, "status_code": 404}
        return {"erro": "Erro interno no Serviço B", "status_code": 500}

# -----------------------------------------------
# 2. Endpoint REST (Agregação Final)
# -----------------------------------------------

@app.get("/infos-sincronas-livro/{livro_id}")
async def get_dados_sincronos(livro_id: str):
    
    # 1. Chamada Síncrona para A e B (idealmente, feita em paralelo para otimizar tempo)
    dados_a = chamar_servico_A(livro_id)
    dados_b = chamar_servico_B(livro_id)
    
    # 2. Verifica se houve erro irrecuperável (ex: livro não existe)
    if "erro" in dados_a:
         raise HTTPException(status_code=dados_a["status_code"], detail=dados_a["erro"])
    
    # 3. Agregação dos dados (a "figurinha" completa síncrona)
    dados_agregados = {
        "livro_id": livro_id,
        "dados_livro": dados_a, 
        "dados_autor": dados_b # Incluindo o B (Autor)
    }
    
    return dados_agregados

# -----------------------------------------------
# Script de Execução
# -----------------------------------------------
if __name__ == "__main__":
    import uvicorn
    print(f"Servidor C pronto. Escutando REST na porta {PORTA_C}...")
    uvicorn.run(app, host="0.0.0.0", port=PORTA_C)