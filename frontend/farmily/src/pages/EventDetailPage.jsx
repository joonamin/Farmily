import chunsik from '../assets/images/chunsik.jpg';
import EventCard from '../components/common/EventCard.jsx';

const TESTITEM = {
  id: 13,
  title: '게시글 제목',
  family: '춘식라이언 가족',
  author: { nickname: '작성자' },
  picture: [
    { image: chunsik, content: '사진1 내용1' },
    { image: chunsik, content: '사진2 내용2' },
    { image: chunsik, content: '사진3 내용2' },
    { image: chunsik, content: '사진4 내용2' },
    { image: chunsik, content: '사진5 내용2' },
    { image: chunsik, content: '사진6 내용2' },
    { image: chunsik, content: '사진7 내용2' },
    { image: chunsik, content: '사진8 내용2' },
    { image: chunsik, content: '사진9 내용2' },
    { image: chunsik, content: '사진10 내용2' },
    { image: chunsik, content: '사진11 내용2' },
  ],
};

export default function EventDetailPage() {
  return (
    <div className="h-full w-full overflow-hidden">
      <div className="h-1/6 p-10">
        <div className="border-4 p-2 border-black rounded-xl text-left">
          <p>{TESTITEM.title}</p>
        </div>
      </div>
      <p className="text-right px-10">작성자 : {TESTITEM.author.nickname}</p>
      <div className="flex h-3/5 snap-x overflow-x-scroll px-6">
        <div className="h-full flex m-auto">
          {TESTITEM.picture.map((item, index) => (
            <EventCard key={index} image={item.image} content={item.content} />
          ))}
        </div>
      </div>
      <div className="">
        <p>댓글공간</p>
      </div>
    </div>
  );
}
