import {ActionsUnion, createAction} from "../../helpers/action.helper";
import {IChatDetails, IChatListElement} from "../../api/chat/chatModels";
import {APPEND_CHAT_DETAILS_CACHED, SET_CHATS_LIST, SET_SELECTED} from "./actionTypes";

export const chatsListActions = {
    setChatsList: (list: IChatListElement[]) => createAction(SET_CHATS_LIST, list),
    removeChatsList: () => createAction(SET_CHATS_LIST, undefined),
    setSelected: (id: string) => createAction(SET_SELECTED, id),
    removeSelected: () => createAction(SET_SELECTED, undefined),
    appendDetailsCached: (details: IChatDetails) => createAction(APPEND_CHAT_DETAILS_CACHED, details),
};

export type ChatsListActions = ActionsUnion<typeof chatsListActions>;
