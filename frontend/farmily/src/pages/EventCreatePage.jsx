import { useState, useEffect } from 'react';

export default function EventCreatePage() {
  const [images, setImages] = useState([]);
  const [imageDescription, setImageDescription] = useState('');

  const handleImageChange = (event) => {
    const files = event.target.files;
    if (files) {
      const newImages = Array.from(files).map((file) => ({
        url: URL.createObjectURL(file),
        description: '',
      }));
      setImages((prevImages) => [...prevImages, ...newImages]);
    }
  };

  const handleImageDescriptionChange = (index, description) => {
    setImages((prevImages) => {
      const updatedImages = [...prevImages];
      updatedImages[index].description = description;
      return updatedImages;
    });
  };

  return (
    <div className="flex flex-col items-center h-full">
      <h1 className="text-2xl font-bold mb-4">이벤트 글쓰기</h1>
      <form className="w-10/12 h-5/6 bg-gray-100 p-1 rounded-lg shadow-md overflow-x-scroll">
        <input
          type="text"
          placeholder="제목"
          className="border border-stone-500 rounded p-2 mb-4 w-full"
        />
        <label className="block mb-4">
          이미지 선택
          <input
            type="file"
            accept="image/*"
            onChange={handleImageChange}
            className="mt-2"
            multiple // 다중 파일 선택을 허용
          />
        </label>
        <div className="flex flex-no-wrap gap-4 overflow-x-auto h-3/4">
          {images.map((image, index) => (
            <div
              key={index}
              className="relative border border-stone-700 flex-shrink-0 snap-center"
            >
              <img
                src={image.url}
                alt={`Selected ${index + 1}`}
                className="object-contain w-48 h-48 mb-2 rounded"
              />
              <input
                type="text"
                placeholder="간단한 설명을 입력하세요"
                value={image.description}
                onChange={(e) =>
                  handleImageDescriptionChange(index, e.target.value)
                }
                className="border border-stone-500 rounded p-2 w-48 absolute bottom-0 left-0 bg-white h-1/3 overflow-hidden whitespace-normal"
              />
            </div>
          ))}
        </div>
      </form>
    </div>
  );
}
