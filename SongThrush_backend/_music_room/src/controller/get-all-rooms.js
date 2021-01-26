module.exports = function makeGetAllRooms({readAllRoom}){
   
    return async function getAllRooms(httpRequest){
        
        const id = httpRequest.id;

        try {
            const readed = await readAllRoom(id);
        
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