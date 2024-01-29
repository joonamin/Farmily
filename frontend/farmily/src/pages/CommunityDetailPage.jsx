import tree from '../assets/images/communityTree.png';

const TESTITEM = {
  id: 13,
  title: '게시글 제목',
  family: '춘식라이언 가족',
  content: '우리 가족 나무입니다~~',
  author: '작성자',
};

export default function CommunityDetailPage() {
  return (
    <div className="h-full">
      <div className="h-1/6 p-10">
        <div className="border-4 p-2 border-black rounded-xl text-left flex justify-between">
          <p>{TESTITEM.title}</p>
          <p>작성자 : {TESTITEM.author}</p>
        </div>
      </div>
      <div className="h-3/6">
        <p className="text-right px-10">{TESTITEM.family} 나무</p>
        <div className="">
          <img src={tree} alt="" className="mx-auto object-cover" />
        </div>
      </div>
      <div className="h-2/6">{TESTITEM.content}</div>
    </div>
  );
}
