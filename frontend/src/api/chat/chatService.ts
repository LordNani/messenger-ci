import faker from "faker";
import {IChatDetails, IChatListElement} from "./chatModels";

const chatService = {

    getChatsList: async (): Promise<IChatListElement[]> => {
        await new Promise(resolve => setTimeout(resolve, 500));
        return new Array(30)
            .fill(null)
            .map(_ => ({
                id: faker.random.uuid(),
                isGroup: faker.random.boolean(),
                title: faker.lorem.sentence(5),
            }));
    },

    getChatDetailsById: async (id: string): Promise<IChatDetails> => {
        await new Promise(resolve => setTimeout(resolve, 500));
        return {
            id,
            isGroup: faker.random.boolean(),
            title: faker.lorem.sentence(5),
            info: faker.lorem.paragraph(5),
        };
    },

};

export default chatService;