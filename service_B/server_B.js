const grpc = require('@grpc/grpc-js');
const protoLoader = require('@grpc/proto-loader');

// --- CONFIGURAÇÕES ---
const PORTA = '0.0.0.0:5002';
const PROTO_PATH = './proto/catalogo_autores.proto';

// Simulação de Banco de Dados
const AUTORES_DB = {
    1: {
        nome_autor: 'David Nicholls',
        idade_autor: 57,
        nacionalidade: 'Britânica',
        obras_conhecidas: ['Um Dia', 'Nós', 'Diário de uma Paixão']
    },
    2: {
        nome_autor: 'Louisa May Alcott',
        idade_autor: 55,
        nacionalidade: 'Americana',
        obras_conhecidas: ['Mulherzinhas', 'Homens Jovens', 'Uma Garota Antiga']
    },
    3: {
        nome_autor: 'Sally Rooney',
        idade_autor: 33,
        nacionalidade: 'Irlandesa',
        obras_conhecidas: ['Pessoas Normais', 'Conversas Entre Amigos', 'Onde Estás, Mundo Belo']
    },
    4: {
        nome_autor: 'Haruki Murakami',
        idade_autor: 76,
        nacionalidade: 'Japonesa',
        obras_conhecidas: ['Norwegian Wood', 'Kafka à Beira-Mar', '1Q84']
    },
    5: {
        nome_autor: 'Blake Crouch',
        idade_autor: 46,
        nacionalidade: 'Americana',
        obras_conhecidas: ['Matéria Escura', 'Recursão', 'Wayward Pines']
    },
    6: {
        nome_autor: 'Machado de Assis',
        idade_autor: 69,
        nacionalidade: 'Brasileira',
        obras_conhecidas: ['Dom Casmurro', 'Memórias Póstumas de Brás Cubas', 'Quincas Borba']
    },
    7: {
        nome_autor: 'Joaquim Manuel de Macedo',
        idade_autor: 59,
        nacionalidade: 'Brasileira',
        obras_conhecidas: ['A Moreninha', 'As Vítimas-Algozes', 'O Moço Loiro']
    }
};

// ------------------------------------------
// Handlers gRPC
// ------------------------------------------

function getInfosAutor(call, callback) {
    const livroId = call.request.livro_id;
    const autor = AUTORES_DB[livroId];

    if (!autor) {
        return callback({
            code: grpc.status.NOT_FOUND,
            details: `Autor para o livro ID ${livroId} não foi encontrado.`
        });
    }

    callback(null, autor);
}

// ------------------------------------------
// Setup e Inicialização do Servidor
// ------------------------------------------

function main() {
    const packageDefinition = protoLoader.loadSync(
        PROTO_PATH,
        { keepCase: true, longs: String, enums: String, defaults: true, oneofs: true }
    );

    const proto = grpc.loadPackageDefinition(packageDefinition).catalogo_autores;

    const server = new grpc.Server();

    server.addService(proto.AutorService.service, {
        GetInfosAutor: getInfosAutor
    });

    server.bindAsync(PORTA, grpc.ServerCredentials.createInsecure(), (err, port) => {
        if (err) {
            console.error(`❌ Erro ao iniciar servidor de Autores: ${err.message}`);
            return;
        }
        console.log(`Servidor de Autores rodando na porta ${port}`);
    });
}

main();
