import grpc
from concurrent import futures
import catalogo_pb2, catalogo_pb2_grpc

LIVROS = {
    1: {
        "titulo": "Um Dia",
        "genero": "Romance contemporâneo",
        "sinopse": "Emma e Dexter se conhecem na formatura e, a cada aniversário dessa data, o livro revisita suas vidas para mostrar como suas escolhas, encontros e desencontros moldam seu relacionamento ao longo de 20 anos.",
        "ano_lancamento": 2009,
        "idioma_original": "Inglês"
    },
    2: {
        "titulo": "Mulherzinhas",
        "genero": "Romance / Literatura clássica",
        "sinopse": "A história acompanha as irmãs March — Meg, Jo, Beth e Amy — enquanto crescem durante a Guerra Civil Americana, lidando com amadurecimento, trabalho, amor e família.",
        "ano_lancamento": 1868,
        "idioma_original": "Inglês"
    },
    3: {
        "titulo": "Pessoas Normais",
        "genero": "Romance dramático",
        "sinopse": "Connell e Marianne vivem uma relação intensa e complexa, indo e voltando um para o outro enquanto tentam se encontrar emocionalmente durante a adolescência e a vida universitária.",
        "ano_lancamento": 2018,
        "idioma_original": "Inglês"
    },
    4: {
        "titulo": "Norwegian Wood",
        "genero": "Romance/Drama psicológico",
        "sinopse": "Ao ouvir 'Norwegian Wood' dos Beatles, Toru Watanabe relembra sua juventude marcada pela relação delicada com Naoko, uma jovem emocionalmente instável, enquanto tenta seguir em frente com sua vida.",
        "ano_lancamento": 1987,
        "idioma_original": "Japonês"
    },
    5: {
        "titulo": "Matéria Escura",
        "genero": "Ficção científica/Thriller",
        "sinopse": "Um físico é sequestrado e levado para uma realidade alternativa onde sua vida tomou um rumo completamente diferente. Ele precisa enfrentar versões de si mesmo e lutar para voltar para sua família.",
        "ano_lancamento": 2016,
        "idioma_original": "Inglês"
    },
    6: {
        "titulo": "Dom Casmurro",
        "genero": "Romance realista",
        "sinopse": "Bentinho revisita sua vida e seu casamento com Capitu, questionando se ela o traiu ou não, numa narrativa marcada por memória, ciúme e ambiguidade.",
        "ano_lancamento": 1899,
        "idioma_original": "Português"
    },
    7: {
        "titulo": "A Moreninha",
        "genero": "Romance romântico",
        "sinopse": "A história segue Augusto, um estudante que, ao visitar a Ilha de Paquetá, conhece Carolina e revive uma antiga promessa de amor feita na infância.",
        "ano_lancamento": 1844,
        "idioma_original": "Português"
    }
}

# CÓDIGO CORRIGIDO (DENTRO DE catalogo_service.py)
    
class CatalogoLivrosService(catalogo_pb2_grpc.InfosLivroServiceServicer):

    def FindAll(self, request, context):
        response = catalogo_pb2.FindAllResponse()

        for id_livro, dados in LIVROS.items():
            resumo = catalogo_pb2.LivroResumo(
                livro_id=id_livro,
                titulo=dados["titulo"]
            )
            response.livros.append(resumo)

        return response
    
    def GetInfosLivro(self, request, context):
        livro_id = request.livro_id

        if livro_id <= 0:
            context.set_details("ID de livro inválido: deve ser um número positivo.")
            context.set_code(grpc.StatusCode.INVALID_ARGUMENT)
            return catalogo_pb2.InfosResponse()

        livro = LIVROS.get(livro_id)
        
        if not livro:
            context.set_details("Livro não encontrado, tente novamente.")
            context.set_code(grpc.StatusCode.NOT_FOUND)
            return catalogo_pb2.InfosResponse()
            
        return catalogo_pb2.InfosResponse(
            titulo= livro["titulo"], 
            genero= livro["genero"], 
            sinopse= livro["sinopse"], 
            ano_lancamento= livro["ano_lancamento"], 
            idioma_original= livro["idioma_original"]
        )
