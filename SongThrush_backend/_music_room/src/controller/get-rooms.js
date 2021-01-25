module.exports = function makeGetRooms({readRoom}){
   
    return async function getRooms(httpRequest){
        
        const id = httpRequest.id;

        try {
            const readed = await readRoom(id);
        
            return {
                headers: {
                    'Content-Type': 'application/json',
                },
                statusCode: 200,
                body: readed
            }            
        
        } catch (e) {
        
            return {
                headers: {
                    'Content-Type': 'application/json',
                },
                statusCode: 400,
                body: e.message
            }
        
        }

    }
}